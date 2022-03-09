package com.solve.movie.service;

import java.util.List;

import com.solve.movie.model.Movie;

public interface MovieService {
	
	public List<Movie> fetchAll();
	public List<Movie> fetchByYear(int year);
	public List<Movie> fetchByRating(int rating);
	public Movie saveOrUpdate(Movie movie);
	public void delete(int id);

}
