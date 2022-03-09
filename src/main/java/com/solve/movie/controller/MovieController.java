package com.solve.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.solve.movie.model.Movie;
import com.solve.movie.service.MovieService;

@RestController
public class MovieController {
	
	@Autowired
	MovieService service;
	
	@GetMapping("/movies")
	public List<Movie> fetchAllMovies() {
		return service.fetchAll();
	}
	
	@GetMapping("/movies/rating/{rating}")
	public List<Movie> fetchAllMoviesByRating(@PathVariable(value = "rating") int rating) {
		return service.fetchByRating(rating);
	}
	
	@GetMapping("/movies/year/{year}")
	public List<Movie> fetchAllMoviesByYear(@PathVariable(value = "year") int year) {
		return service.fetchByYear(year);
	}
	
	@PutMapping("/movies")
	public Movie saveOrUpdateMovie(@RequestBody Movie movie) {
		return service.saveOrUpdate(movie);
	}
	
	@DeleteMapping("/movies/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable int id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

}
