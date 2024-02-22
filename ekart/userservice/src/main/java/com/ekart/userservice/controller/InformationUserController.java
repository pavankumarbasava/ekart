package com.ekart.userservice.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.userservice.entity.Role;
import com.ekart.userservice.entity.RoleName;
import com.ekart.userservice.entity.User;
import com.ekart.userservice.http.HeaderGeneration;
import com.ekart.userservice.mapper.UserMapper;
import com.ekart.userservice.model.dto.model.UserDto;
import com.ekart.userservice.model.dto.request.SignInForm;
import com.ekart.userservice.model.dto.request.SignUpForm;
import com.ekart.userservice.repository.RoleRepository;
import com.ekart.userservice.repository.UserRepository;
import com.ekart.userservice.security.jwt.JwtProvider;
import com.ekart.userservice.service.impl.UserServiceImpl;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("api/information")
public class InformationUserController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final UserRepository userRepository;
    private final HeaderGeneration headerGenerator;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;

    private final RoleRepository roleRepo;

    private static final Logger logger= LoggerFactory.getLogger(InformationUserController.class);
	

    
    @Autowired
    public InformationUserController(UserRepository userRepository, HeaderGeneration headerGenerator, JwtProvider jwtProvider, AuthenticationManager authenticationManager, UserServiceImpl userService,RoleRepository roleRepo) {
        this.userRepository = userRepository;
        this.headerGenerator = headerGenerator;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleRepo =roleRepo;
    }

    @GetMapping(value = "/user/username")
    public ResponseEntity<?> getUserByUsername(@RequestParam("name") String username) {
    	  logger.info("User name "+username);
        UserDto user = UserMapper.map(userService.getUserFromUserName(username));
        logger.info("User data from get user from user name "+user.toString());
        return (user != null)
                ? new ResponseEntity<>(user, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK)
                : new ResponseEntity<>(null, headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
    }

  
    @GetMapping(value = "/user")
    public ResponseEntity<?> getUserById(@RequestParam("id") Long id) {
        if (userService.findById(id).isPresent()) {
            User user = userService.findById(id).get();
            return new ResponseEntity<>(user,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(null, headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value="/user/current")
    public  ResponseEntity<?>  getUserFromToken( Principal principle) {//,  @RequestHeader(name = "Authorization") String authorizationToken) {
    	String userNameFromToken = principle.getName();
   
    	
    	if (userService.findByUsername(userNameFromToken).isPresent()) {
            UserDto user =  UserMapper.map(userService.findByUsername(userNameFromToken).get());
            return new ResponseEntity<>(user,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(null, headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
    
       
    }

	@PutMapping(value = "/user/update")
	public ResponseEntity<User> update(@RequestBody SignUpForm userDto, Principal principle) {

		User user = userService.getUserFromUserName(principle.getName());
	
		user.setName(userDto.getName());
		user.setGender(userDto.getGender());
		 

        List<Role> roles = new ArrayList<>();
        userDto.getRoles().forEach(role -> {
            RoleName roleName = switch (role) {
                case "ADMIN" -> RoleName.ROLE_ADMIN;
                case "PM" -> RoleName.ROLE_PM;
                case "USER" -> RoleName.ROLE_USER;
                default -> null;
            };
        

            Role userRole = roleRepo.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found database."));

            roles.add(userRole);
        });
		user.setRoles(roles);
		user.setUpdatedAt(LocalDateTime.now());

		return ResponseEntity.ok(this.userService.update(user));
	}
    
    
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/user/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> listUsers = userService.getAllUsers()
                .orElseThrow(() -> new UsernameNotFoundException("Not Found List User"));
        return new ResponseEntity<>(listUsers,
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK);
    }
//
//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/user/page")
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<User> users = userService.getAllUsers(page, size);
        return new ResponseEntity<>(users, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
    }
//
    @GetMapping("/generate/token")
    public ResponseEntity<String> getToken(@RequestBody SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsernameOrEmail(), signInForm.getPassword())
        );

        String token = jwtProvider.createToken(authentication); // Đoạn mã ở đây để lấy token từ hệ thống xác thực (nếu cần)
        return ResponseEntity.ok(token); // Trả về token trong phản hồi
    }

    @GetMapping("/token")
    public String getUsernameFromToken(@RequestParam("token") String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
