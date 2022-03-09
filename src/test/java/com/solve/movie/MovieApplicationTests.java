package com.solve.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solve.movie.controller.MovieController;
import com.solve.movie.model.Movie;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class MovieApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	MovieController movieController;
	
	ObjectMapper mapper = new ObjectMapper();

	@Test
	@Order(1)
	void contextLoads() {
		assertThat(movieController).isNotNull();
	}
	
	@Test
	@Order(2)
	public void shouldReturnAllMovies() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/movies")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(5)))
		.andExpect(jsonPath("$[2].name", is("Movie3")));
	}
	
	@Test
	@Order(3)
	public void shouldReturnAllMoviesByRating() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/movies/rating/5")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].name", is("Movie1")));
	}
	
	@Test
	@Order(4)
	public void shouldReturnAllMoviesByYear() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/movies/year/2009")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].name", is("Movie1")))
		.andExpect(jsonPath("$[1].name", is("Movie2")));
	}
	
	@Test
	@Order(5)
	public void shouldReturnResourceNotFoundWhenSearchingByRating() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/movies/rating/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	@Order(6)
	public void shouldReturnResourceNotFoundWhenSearchingByYear() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/movies/year/2008")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	@Order(7)
	public void shouldSaveNewMovie() throws Exception {
		Movie movie = new Movie(null, "Movie6", 2007, 6);
		mockMvc.perform(MockMvcRequestBuilders
				.put("/movies")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(movie)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.name", is("Movie6")));
	}
	
	@Test
	@Order(8)
	public void shouldUpdateExistingMovie() throws Exception {
		Movie movie = new Movie(5, "Movie6", 2007, 6);
		mockMvc.perform(MockMvcRequestBuilders
				.put("/movies")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(movie)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.name", is("Movie6")));
	}
	
	@Test
	@Order(9)
	public void shouldDeleteExistingMovie() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/movies/5")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

}
