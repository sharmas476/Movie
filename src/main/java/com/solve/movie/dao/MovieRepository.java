package com.solve.movie.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.solve.movie.model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {
	
	public List<Movie> findAll();
	
	public List<Movie> findByName(String name);
	
	public List<Movie> findByYear(int year);
	
	public List<Movie> findByRatings(int ratings);
}
