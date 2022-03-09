package com.solve.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solve.movie.dao.MovieRepository;
import com.solve.movie.exception.ResourceNotFoundException;
import com.solve.movie.model.Movie;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	MovieRepository repository;
	
	@Override
	public List<Movie> fetchAll() {
		return repository.findAll();
	}

	@Override
	public List<Movie> fetchByYear(int year) {
		List<Movie> list = repository.findByYear(year);
		if (null == list || list.size() == 0) {
			throw new ResourceNotFoundException("Movie", "Year", year);
		}
		return list;
	}

	@Override
	public List<Movie> fetchByRating(int rating) {
		List<Movie> list = repository.findByRatings(rating);
		if (null == list || list.size() == 0) {
			throw new ResourceNotFoundException("Movie", "Rating", rating);
		}
		return list;
	}

	@Override
	public Movie saveOrUpdate(Movie movie) {
		if (movie.getId() == null || movie.getId() == 0) {
			// save new movie
			return repository.save(movie);
		} else {
			// update existing movie
			Movie savedMovie = repository.findById(movie.getId()).orElseThrow(() -> new ResourceNotFoundException("Movie", "Id", movie.getId()));
			savedMovie.setName(movie.getName());
			savedMovie.setRatings(movie.getRatings());
			savedMovie.setYear(movie.getYear());
			return repository.save(movie);
		}
		
	}

	@Override
	public void delete(int id) {
		repository.deleteById(id);
	}

}
