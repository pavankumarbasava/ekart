package com.ekart.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ekart.userservice.security.jwt.JwtEntryPoint;
import com.ekart.userservice.security.jwt.JwtTokenFilter;
import com.ekart.userservice.security.userprinciple.UserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtEntryPoint jwtEntryPoint;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtTokenFilter jwtTokenFilter() {
		return new JwtTokenFilter();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {

		return super.authenticationManager();
	}
	@Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailService();
    }
	 @Bean
	    public AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsService());
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        return authenticationProvider;
	    }
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		 httpSecurity
         .cors() // Cross-Origin Resource Sharing (CORS)
         .and()
         .csrf().disable()  // CSRF (Cross-Site Request Forgery)
         .authorizeRequests()
         .antMatchers("/api/auth/**").permitAll()
         .antMatchers("/api/manager/token").permitAll()
         .anyRequest().authenticated()
         .and()
         .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

 httpSecurity.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
