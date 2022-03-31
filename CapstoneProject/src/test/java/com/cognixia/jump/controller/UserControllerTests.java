package com.cognixia.jump.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ComponentScan(basePackages = {"com.cognixia.jump"})
public class UserControllerTests {
	private final String STARTING_URI = "http://localhost:8080/api";
	
	@MockBean
	private UserService userserv;
	
	@MockBean
	UserDetailsService userDetailService;
	
	@InjectMocks
	private UserController usercontroller;
	
//	@Autowired
//	private ObjectMapper mapper;
	
	@MockBean
	JwtUtil jwtUtil;
	
	@Autowired
	private MockMvc mvc;
	
	
	@Test
	@WithMockUser(username = "admin", roles = {"USER","ADMIN"})
	void testHealth() throws Exception {
		String uri = STARTING_URI + "/health";


		mvc.perform(get(uri)) // perform request ..
				.andDo(print()) // ..then print request sent and response returned
				.andExpect(status().isOk()); // expect 200 status code
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"USER","ADMIN"})
	void testGetUsers() throws Exception {
		String uri = STARTING_URI + "/user";
		
		List<User> testUsers = Arrays.asList(new User("user1", "user1@gmail.com", "password"), new User("user2", "user2@gmail.com", "password"));
		
		when(userserv.getAllUsers()).thenReturn(testUsers);
		
		mvc.perform(get(uri))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(testUsers.size()));
		verify(userserv, times(1)).getAllUsers(); // getAllUsers() was used once
		verifyNoMoreInteractions(userserv); // after checking above, check service is no longer being used
		
	}

	@Test
	@WithMockUser(username = "admin", roles = {"USER","ADMIN"})
	void testGetUserByName() throws Exception {
		String uri = STARTING_URI + "/user/user1";
		
		User test = new User("user1", "user1@gmail.com", "password");
		test.setId(1);
		
		when(userserv.getUserByName("user1")).thenReturn(Optional.of(test));
		
		mvc.perform(get(uri))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username").value("user1"));
		
		verify(userserv, times(1)).getUserByName("user1");
		verifyNoMoreInteractions(userserv);
	}
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"USER","ADMIN"})
//	void testGetUserByEmail() throws Exception {
//		String uri = STARTING_URI + "/user/email/";
//	
//	}
}
