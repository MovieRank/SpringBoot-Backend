package com.example.MovieRank.Controllers;

import com.example.MovieRank.DTO.Movie.MovieData;
import com.example.MovieRank.DTO.Movie.MovieListData;
import com.example.MovieRank.DTO.User.Request.LoginData;
import com.example.MovieRank.DTO.User.Response.UserTokenData;
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
    public ResponseEntity<List<MovieListData>> getAllMovies(@PathVariable(name = "page") Long page) {

        List<MovieListData> movieListData = movieService.getAllMovies(page);
        return ResponseEntity.ok(movieListData);
    }

    @GetMapping("/get/popular/{page}")
    public ResponseEntity<List<MovieListData>> getPopularMovies(@PathVariable(name = "page") Long page) {

        List<MovieListData> movieListData = movieService.getPopularMovies(page);
        return ResponseEntity.ok(movieListData);
    }

    @GetMapping("/get/top/{page}")
    public ResponseEntity<List<MovieListData>> getTopMovies(@PathVariable(name = "page") Long page) {

        List<MovieListData> movieListData = movieService.getTopMovies(page);
        return ResponseEntity.ok(movieListData);
    }

    @GetMapping("/get/upcoming/{page}")
    public ResponseEntity<List<MovieListData>> getUpcomingMovies(@PathVariable(name = "page") Long page) {

        List<MovieListData> movieListData = movieService.getUpcomingMovies(page);
        return ResponseEntity.ok(movieListData);
    }
}
