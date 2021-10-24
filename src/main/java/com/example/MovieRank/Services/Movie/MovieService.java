package com.example.MovieRank.Services.Movie;

import com.example.MovieRank.DTO.Movie.MovieData;
import com.example.MovieRank.DTO.Movie.MovieListData;
import com.example.MovieRank.Exceptions.RequestErrorException;
import com.example.MovieRank.Repositories.*;
import com.example.MovieRank.Services.Movie.CollectionCreateClass.CollectionCreateClass;
import com.example.MovieRank.Services.Movie.HttpRequestClass.HttpRequestClass;
import com.example.MovieRank.Services.Movie.ImageDownloadClass.ImageDownloadClass;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(MovieService.class);

    public MovieData getMovie(Long movieId) {

        JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL").body());

        byte[] backdropPath = ImageDownloadClass.getImage("http://image.tmdb.org/t/p/original" + jsonObject.getString("backdrop_path"));
        byte[] posterPath = ImageDownloadClass.getImage("http://image.tmdb.org/t/p/original" + jsonObject.getString("poster_path"));

        return MovieData.builder()
                .movieId(jsonObject.getLong("id"))
                .originalLanguage(jsonObject.getString("original_language"))
                .originalTitle(jsonObject.getString("original_title"))
                .title(jsonObject.getString("title"))
                .overview(jsonObject.getString("overview"))
                .runtime(jsonObject.getLong("runtime"))
                .releaseDate(Date.valueOf(jsonObject.getString("release_date")))
                .backdropPath(backdropPath)
                .posterPath(posterPath)
                .homePage(jsonObject.getString("homepage"))
                .budget(jsonObject.getLong("budget"))
                .revenue(jsonObject.getLong("revenue"))
                .voteAverage(0.0)
                .voteCount(0L)
                .status(jsonObject.getString("status"))
                .categories(CollectionCreateClass.createCategoriesCollection(jsonObject.getJSONArray("genres")))
                .productionCountries(CollectionCreateClass.createProductionCountriesCollection(jsonObject.getJSONArray("production_countries")))
                .productionCompanies(CollectionCreateClass.createProductionCompaniesCollection(jsonObject.getJSONArray("production_companies")))
                .build();
    }

    public List<MovieListData> getPopularMovies(Long page) {

        JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest("https://api.themoviedb.org/3/movie/popular?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL&page=" + page).body());
        JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("results"));

        List<MovieListData> movieListData = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject movie = jsonArray.getJSONObject(i);
            byte[] posterPath = ImageDownloadClass.getImage("http://image.tmdb.org/t/p/original" + movie.getString("poster_path"));
            movieListData.add(MovieListData.builder()
                    .movieId(movie.getLong("id"))
                    .title(movie.getString("title"))
                    .releaseDate(Date.valueOf(movie.getString("release_date")))
                    .posterPath(posterPath)
                    .voteAverage(0.0)
                    .voteCount(0L)
                    .build());
        }

        return movieListData;
    }

    public List<MovieListData> getTopMovies(Long page) {

        JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest("https://api.themoviedb.org/3/movie/top_rated?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL&page=" + page).body());
        JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("results"));

        List<MovieListData> movieListData = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject movie = jsonArray.getJSONObject(i);
            byte[] posterPath = ImageDownloadClass.getImage("http://image.tmdb.org/t/p/original" + movie.getString("poster_path"));
            movieListData.add(MovieListData.builder()
                    .movieId(movie.getLong("id"))
                    .title(movie.getString("title"))
                    .releaseDate(Date.valueOf(movie.getString("release_date")))
                    .posterPath(posterPath)
                    .voteAverage(0.0)
                    .voteCount(0L)
                    .build());
        }

        return movieListData;
    }

    public List<MovieListData> getUpcomingMovies(Long page) {

        JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest("https://api.themoviedb.org/3/movie/upcoming?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL&page=" + page).body());
        JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("results"));

        List<MovieListData> movieListData = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject movie = jsonArray.getJSONObject(i);
            byte[] posterPath = ImageDownloadClass.getImage("http://image.tmdb.org/t/p/original" + movie.getString("poster_path"));
            movieListData.add(MovieListData.builder()
                    .movieId(movie.getLong("id"))
                    .title(movie.getString("title"))
                    .releaseDate(Date.valueOf(movie.getString("release_date")))
                    .posterPath(posterPath)
                    .voteAverage(0.0)
                    .voteCount(0L)
                    .build());
        }

        return movieListData;
    }

    public List<MovieListData> getAllMovies(Long page) {

        List<MovieListData> movieListData = new ArrayList<>();
        for (long i = 101+((page-1)*20); i < 121+((page-1)*20); i++) {

            JSONObject movie = new JSONObject(HttpRequestClass.sendRequest("https://api.themoviedb.org/3/movie/" + i + "?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL").body());
            if (!movie.has("poster_path") || movie.isNull("poster_path")) continue;

            byte[] posterPath = ImageDownloadClass.getImage("http://image.tmdb.org/t/p/original" + movie.getString("poster_path"));
            movieListData.add(MovieListData.builder()
                    .movieId(movie.getLong("id"))
                    .title(movie.getString("title"))
                    .releaseDate(Date.valueOf(movie.getString("release_date")))
                    .posterPath(posterPath)
                    .voteAverage(0.0)
                    .voteCount(0L)
                    .build());
        }

        return movieListData;
    }
}
