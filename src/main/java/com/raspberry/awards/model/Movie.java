package com.raspberry.awards.model;

import com.raspberry.awards.util.StringSetConverter;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie_list")
public class Movie {

    @Id
    private Long id;

    @Column(name = "year_")
    private Year year;

    private String title;

    @Convert(converter = StringSetConverter.class)
    private Set<String> studios = new HashSet<>();

    @Convert(converter = StringSetConverter.class)
    private Set<String> producers = new HashSet<>();

    @Nullable
    private String winner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getStudios() {
        return studios;
    }

    public void setStudios(Set<String> studios) {
        this.studios = studios;
    }

    public Set<String> getProducers() {
        return producers;
    }

    public void setProducers(Set<String> producers) {
        this.producers = producers;
    }

    @Nullable
    public String getWinner() {
        return winner;
    }

    public void setWinner(@Nullable String winner) {
        this.winner = winner;
    }
}
