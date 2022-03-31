package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filter.JwtRequestFilter;

// this class will detail how spring security is going to handle authorization and authentication
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure( AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService);
		
	}
	
	// Authorization ( what do you want)
	// which user have access to which uris (APIs)
	@Override
	protected void configure( HttpSecurity http) throws Exception{
		
		// more specific authorization on top and 
		// broader specification on the bottom
		
		// if creating an ADMIN type user, makes sure to put them in every antMatcher(), so they get access
		
		http.csrf().disable()
			.authorizeRequests()
			// all auth routes
			.antMatchers( HttpMethod.GET, 	"/api/health").permitAll()		
			.antMatchers( HttpMethod.POST,  "/api/register").permitAll() //sign up
			.antMatchers( HttpMethod.POST,  "/api/authenticate").permitAll() // sign in
			//all users route
			.antMatchers( HttpMethod.PUT, 	 "/api/users").hasAnyRole("USER", "ADMIN") 
			.antMatchers( HttpMethod.GET, 	 "/api/user/**").hasAnyRole("USER", "ADMIN")
			.antMatchers( HttpMethod.DELETE, "/api/user/**").hasAnyRole("USER", "ADMIN")
			.antMatchers( HttpMethod.GET, 	 "/api/users").hasRole("ADMIN")
			//all airlines routes
			.antMatchers( HttpMethod.GET, 		"/api/airline/**").hasAnyRole("USER", "ADMIN")
			.antMatchers( HttpMethod.POST, 		"/api/airline/**").hasAnyRole("ADMIN")
			.antMatchers( HttpMethod.DELETE, 	"/api/airline/**").hasAnyRole("ADMIN")
			.antMatchers( HttpMethod.PUT, 		"/api/airline/**").hasAnyRole("ADMIN")
			// all reviews routes
			.antMatchers( HttpMethod.GET, 		"/api/review").permitAll()
			.antMatchers( HttpMethod.GET, 		"/api/review/**").hasAnyRole("USER", "ADMIN")
			.antMatchers( HttpMethod.POST, 		"/api/review/**").hasAnyRole("USER", "ADMIN")
			.antMatchers( HttpMethod.DELETE, 	"/api/review/**").hasAnyRole("USER", "ADMIN")
			.antMatchers( HttpMethod.PUT, 		"/api/review/**").hasAnyRole("USER", "ADMIN")
			//swagger
			.antMatchers( HttpMethod.GET, 	"/v3/api-docs/").hasAnyRole("USER", "ADMIN")
			//Admin User all access
			.antMatchers("/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
			http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
			
			http.cors();
	}
	
	// mainly used to decode passwords
	@Bean
	public PasswordEncoder passwordEncoder() {
		// when password is going to be encoded and decoded, don't user any encode, the password should be clean
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		// create no password encoder, old way of setting it up, still works just deprecated
		//return NoOpPasswordEncoder.getInstance();
		
		// BCrypt encoder to do proper encoding ( very simple to set up and can use others in a similar way)
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
}