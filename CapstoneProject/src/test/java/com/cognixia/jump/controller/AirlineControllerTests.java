package com.cognixia.jump.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognixia.jump.model.Airline;
import com.cognixia.jump.repository.AirlineRepository;
import com.cognixia.jump.service.AirlineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ComponentScan(basePackages = {"com.cognixia.jump"})
public class AirlineControllerTests {
private final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
    ObjectMapper mapper;
	
	@MockBean
	private AirlineService serv;
	
	@InjectMocks
	private AirlineController controller;
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	@Test
	@WithMockUser(username = "admin", roles = {"USER","ADMIN"})
	void testCreateAirline() throws Exception {
		String uri = STARTING_URI + "/airline";
		
		Airline al = new Airline("Airline1");
		
		mvc.perform( MockMvcRequestBuilders
			      .post(uri)
			      .content(asJsonString(al))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isCreated());
	
		verify(serv, times(1)).createAirline(al);
		verifyNoMoreInteractions(serv);
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"USER","ADMIN"})
	void testGetAllAirlines() throws Exception {
		String uri = STARTING_URI + "/airline";

		List<Airline> airlines = Arrays.asList( 
				new Airline("Airline1"), 
				new Airline("Airline2"));
		when( serv.getAllAirlines() ).thenReturn(airlines);
		
		mvc.perform( get(uri) ) // perform request...
        .andDo( print() )   // ...then print request sent and response returned
        .andExpect( status().isOk() ) // expect 200 status code
        .andExpect( jsonPath("$.length()").value(airlines.size() ) )
        .andExpect( jsonPath("$[0].airlineName").value( airlines.get(0).getAirlineName() ) )
		.andExpect( jsonPath("$[1].airlineName").value( airlines.get(1).getAirlineName() ) );

		verify(serv, times(1)).getAllAirlines();
		verifyNoMoreInteractions(serv);
	}
	
	
	@Test
	@WithMockUser(username = "admin", roles = {"USER","ADMIN"})
	void testGetAirlineByID() throws Exception {
		int id = 0;
		String uri = STARTING_URI + "/airline/id/" + id;
		Optional<Airline> al = Optional.of(new Airline("Airline1"));
		
		when( serv.getAirlineById(id) ).thenReturn(al);
		
		mvc.perform( get(uri) ) // perform request...
        .andDo( print() )   // ...then print request sent and response returned
        .andExpect( status().isOk() ) // expect 200 status code
		.andExpect( jsonPath("$.airlineName").value(al.get().getAirlineName() ) );
		
		verify(serv, times(1)).getAirlineById(id);
		verifyNoMoreInteractions(serv);
	}
	
	@Test 
	@WithMockUser(username = "admin", roles = {"USER","ADMIN"})
	void testGetAirlineByName() throws Exception {
		String name = "Airline2";
		String uri = STARTING_URI + "/airline/" + name;
		Optional<Airline> al = Optional.of(new Airline("Airline1"));
		
		when(serv.getAirlineByName(name)).thenReturn(al);
		
		mvc.perform( get(uri) ) // perform request...
        .andDo( print() )   // ...then print request sent and response returned
        .andExpect( status().isOk() ) // expect 200 status code
		.andExpect( jsonPath("$.airlineName").value(al.get().getAirlineName() ) );
		
		verify(serv, times(1)).getAirlineByName(name);
		verifyNoMoreInteractions(serv);
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"USER","ADMIN"})
	void testUpdateAirline() throws Exception {
		String uri = STARTING_URI + "/airline/";
		Airline al = new Airline("Airline1");
		al.setAirlineID(1);
		
		MockHttpServletRequestBuilder mockRequest = put(uri)
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(al));
		
		when(serv.updateAirline(al)).thenReturn(al);
		
		mvc.perform(mockRequest)
		.andDo(print())
		.andExpect( status().is(202) )
		.andExpect( jsonPath("$").value("Airline " + al.getAirlineName() + " was updated") );	       
        
		verify(serv, times(1)).updateAirline(al);
		verifyNoMoreInteractions(serv);
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"USER","ADMIN"})
	void testDeleteAirline() throws Exception {
		int id = 1;
		Airline al = new Airline("Airline1");
		al.setAirlineID(id);
		String uri = STARTING_URI + "/airline/" + al.getAirlineName();
		
		when(serv.getAirlineByName(al.getAirlineName())).thenReturn(Optional.of(al));
		when(serv.deleteAirlineById(id)).thenReturn(true);
		mvc.perform(delete(uri)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
				.andExpect(jsonPath("$").value("Airline:" + al.getAirlineName() + " was deleted"));
		
		verify(serv, times(1)).getAirlineByName(al.getAirlineName());
		verify(serv, times(1)).deleteAirlineById(id);
		verifyNoMoreInteractions(serv);
	}
	
}
