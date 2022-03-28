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
	
	
	// handle the Authentication( who are you?)
	// lookup if the credentials ( username and password) passed through the request match any of the 
	// users for this service
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
			.antMatchers( HttpMethod.GET, "/api/health").permitAll()	
			.antMatchers( HttpMethod.GET, "/v3/api-docs/").hasAnyRole("USER", "ADMIN")
			.antMatchers( HttpMethod.POST, "/api/users/login").permitAll()
			.antMatchers( HttpMethod.POST, "/api/users").permitAll() //sign up
			.antMatchers( HttpMethod.PUT, "/api/users").hasAnyRole("USER", "ADMIN")
			.antMatchers( HttpMethod.GET, "/api/user/**").hasAnyRole("USER", "ADMIN")
			.antMatchers( HttpMethod.DELETE, "/api/user/**").hasAnyRole("USER", "ADMIN")
			.antMatchers( HttpMethod.GET, "/api/orders/**").hasAnyRole("USER", "ADMIN")
			.antMatchers( HttpMethod.POST, "/api/authenticate").permitAll()	
			.antMatchers( HttpMethod.GET, "/api/products").permitAll() //everyone can access product list
			.antMatchers( HttpMethod.POST, "/api/products").hasRole("ADMIN") //everyone can access product list
			.antMatchers( HttpMethod.GET, "/api/orders").hasAnyRole("USER")
			.antMatchers( HttpMethod.GET, "/api/orders/**").hasAnyRole("USER")
			.antMatchers( HttpMethod.POST, "/api/orders").hasAnyRole("USER")
			.antMatchers( HttpMethod.GET, "/api/users").hasRole("ADMIN")
			.antMatchers("/api/hello").hasAnyRole("USER","ADMIN")
			.antMatchers("/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		
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