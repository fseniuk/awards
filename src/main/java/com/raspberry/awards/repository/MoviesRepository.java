package com.raspberry.awards.repository;

import com.raspberry.awards.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepository extends JpaRepository<Movie, String> {
}
