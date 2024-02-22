package com.ekart.userservice.security.userprinciple;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ekart.userservice.entity.User;
import com.ekart.userservice.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
			    .orElseThrow(() -> new UsernameNotFoundException("User not found, username and password: " + username));
	    
	    
	return UserPrinciple.build(user);
	
	}
	  @Transactional
	    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
	        User user = userRepository.findByEmail(email)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found, email and password: " + email));

	        return UserPrinciple.build(user);
	    }

	    @Transactional
	    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
	        User user = userRepository.findByEmail(phone)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found, phone and password: " + phone));

	        return UserPrinciple.build(user);
	    }

}
