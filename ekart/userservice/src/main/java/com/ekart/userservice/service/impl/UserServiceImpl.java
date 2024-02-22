package com.ekart.userservice.service.impl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.LogManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import com.ekart.userservice.avatar.AvatarStore;
import com.ekart.userservice.client.CartFeignClient;
import com.ekart.userservice.entity.Role;
import com.ekart.userservice.entity.RoleName;
import com.ekart.userservice.entity.User;
import com.ekart.userservice.model.dto.model.TokenManager;
import com.ekart.userservice.model.dto.request.SignInForm;
import com.ekart.userservice.model.dto.request.SignUpForm;
import com.ekart.userservice.model.dto.response.InformationMessage;
import com.ekart.userservice.model.dto.response.JwtResponseMessage;
import com.ekart.userservice.repository.RoleRepository;
import com.ekart.userservice.repository.UserRepository;
import com.ekart.userservice.security.jwt.JwtProvider;
import com.ekart.userservice.security.userprinciple.UserDetailService;
import com.ekart.userservice.security.userprinciple.UserPrinciple;
import com.ekart.userservice.security.validate.TokenValidate;
import com.ekart.userservice.service.UserService;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final TokenManager tokenManager;
    private final UserDetailService userDetailsService;
   

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @Value("${refresh.token.url}")
    private String refreshTokenUrl;

    @Autowired
    public UserServiceImpl(CartFeignClient cartFeignClient,UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider, TokenManager tokenManager, UserDetailService userDetailsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.tokenManager = tokenManager;
        this.userDetailsService = userDetailsService;
    }

    // register user
    @Override
    public User registerUser(SignUpForm signUpForm) {
     
           
    	  List<Role> roles = new ArrayList<>();
            signUpForm.getRoles().forEach(role -> {
                RoleName roleName = switch (role) {
                    case "ADMIN" -> RoleName.ROLE_ADMIN;
                    case "PM" -> RoleName.ROLE_PM;
                    case "USER" -> RoleName.ROLE_USER;
                    default -> null;
                };
            

                Role userRole = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found database."));

                roles.add(userRole);
            });

            // check gender user login:
            boolean checkGender = false;
            if (Objects.equals(signUpForm.getGender(), "male")
                    || Objects.equals(signUpForm.getGender(), "Male")
                    || Objects.equals(signUpForm.getGender(), "MALE")) {
                checkGender = true;
            }

           
            User user = User.builder()
                    .name(signUpForm.getName())
                    .username(signUpForm.getUsername())
                    .email(signUpForm.getEmail())
                    .password(passwordEncoder.encode(signUpForm.getPassword()))
                    .avatar(checkGender ? AvatarStore.MALE : AvatarStore.FEMALE)
                    .roles(roles)
                    .gender(signUpForm.getGender())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            User savedUser = userRepository.save(user);
            
            return savedUser;
       
    }

    @Override
    public Mono<JwtResponseMessage> login(SignInForm signInForm) {
        return Mono.defer(() -> {
            String usernameOrEmail = signInForm.getUsernameOrEmail();
            boolean isEmail = usernameOrEmail.contains("@");

            UserDetails userDetails;
            if (isEmail) {
                userDetails = userDetailsService.loadUserByEmail(usernameOrEmail);
            } else {
                userDetails = userDetailsService.loadUserByUsername(usernameOrEmail);
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    signInForm.getPassword(),
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate token and refresh token using JwtProvider
            String accessToken = jwtProvider.createToken(authentication);
            String refreshToken = jwtProvider.createRefreshToken(authentication);

            UserPrinciple userPrinciple = (UserPrinciple) userDetails;

            // Store the token and refresh token using TokenManager
            tokenManager.storeToken(userPrinciple.getUsername(), accessToken);
            tokenManager.storeRefreshToken(userPrinciple.getUsername(), refreshToken);

            JwtResponseMessage jwtResponseMessage = JwtResponseMessage.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)

                    .build();

            return Mono.just(jwtResponseMessage);
        });
    }

    public JwtResponseMessage refreshToken(String refreshToken) {
    	JwtResponseMessage jwtResponseMessage= new JwtResponseMessage();
		try {
			if (refreshToken != null && jwtProvider.validateToken(refreshToken)) {
				String username = jwtProvider.getUserNameFromToken(refreshToken);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				// authenticationToken.setDetails(new
				// WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

				// Tạo mới refresh token
				String accessToken = jwtProvider.createToken(authenticationToken);
				refreshToken = jwtProvider.createRefreshToken(authenticationToken);

				UserPrinciple userPrinciple = (UserPrinciple) userDetails;

				// Store the token and refresh token using TokenManager
				tokenManager.storeToken(userPrinciple.getUsername(), accessToken);
				tokenManager.storeRefreshToken(userPrinciple.getUsername(), refreshToken);

				jwtResponseMessage.setAccessToken(accessToken);
				jwtResponseMessage.setRefreshToken(refreshToken);
				
			}

		} catch (Exception e) {
			logger.error("Can't set user authentication -> Message: ", e);
		}
    	
		return jwtResponseMessage;
    	
  }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
    
    public Optional<User> findByUsername(String userName) {
        return Optional.ofNullable(userRepository.findByUsername(userName))
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }


    
    
    // get all user in list user
    public Optional<List<User>> getAllUsers() {
        return Optional.ofNullable(userRepository.findAll());
    }


    // load user by page and size
    public Page<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
	public void updateActivationFlag(boolean flag,String name) {
		userRepository.updateActivationFlag(flag, name);
		
	}

	public User getUserFromUserName(String userName) {
		logger.info("User name before repo call"+userName);
		User user = userRepository.findByUsername(userName).get();
		logger.info("User name after repo call"+user.toString());
		return user;
		
	}

	public User update(User userDto) {
		return userRepository.save(userDto);
		
	}
	
//	public boolean updateActiveOrBlockedFlags(boolean flag, String fieldName) {
//		
//		userRepository.updateActiveOrBlockedFlags(flag,fieldName);
//		return true;
//	}

	

}
