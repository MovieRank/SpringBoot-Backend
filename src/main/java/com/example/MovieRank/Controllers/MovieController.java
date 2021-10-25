package com.example.MovieRank.Controllers;

import com.example.MovieRank.DTO.Movie.MovieData;
import com.example.MovieRank.DTO.Movie.MovieListItem;
import com.example.MovieRank.Services.Movie.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) { this.movieService = movieService; }

    @GetMapping("/get/{id}")
    public ResponseEntity<MovieData> getMovie(@PathVariable(name = "id") Long movieId) {

        MovieData movieData = movieService.getMovie(movieId);
        return ResponseEntity.ok(movieData);
    }

    @GetMapping("/get/all/{page}")
    public ResponseEntity<List<MovieListItem>> getAllMovies(@PathVariable(name = "page") Long page) {

        List<MovieListItem> movieListData = movieService.getAllMovies(page);
        return ResponseEntity.ok(movieListData);
    }

    @GetMapping("/get/popular/{page}")
    public ResponseEntity<List<MovieListItem>> getPopularMovies(@PathVariable(name = "page") Long page) {

        List<MovieListItem> movieListData = movieService.getPopularMovies(page);
        return ResponseEntity.ok(movieListData);
    }

    @GetMapping("/get/top/{page}")
    public ResponseEntity<List<MovieListItem>> getTopMovies(@PathVariable(name = "page") Long page) {

        List<MovieListItem> movieListData = movieService.getTopMovies(page);
        return ResponseEntity.ok(movieListData);
    }

    @GetMapping("/get/upcoming/{page}")
    public ResponseEntity<List<MovieListItem>> getUpcomingMovies(@PathVariable(name = "page") Long page) {

        List<MovieListItem> movieListData = movieService.getUpcomingMovies(page);
        return ResponseEntity.ok(movieListData);
    }
}
