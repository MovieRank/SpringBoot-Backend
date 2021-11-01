package com.example.MovieRank.Services.Movie.ObjectCreateClass;

import com.example.MovieRank.DTO.Movie.MovieListItem;
import com.example.MovieRank.Services.Movie.CollectionCreateClass.CollectionCreateClass;
import com.example.MovieRank.Services.Movie.ImageDownloadClass.ImageDownloadClass;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;

public class ObjectCreateClass {

    static Logger logger = LoggerFactory.getLogger(CollectionCreateClass.class);

    public static final String getMovieImageURL = "http://image.tmdb.org/t/p/original";

    public static MovieListItem createMovieListItem(JSONObject movie) {

        byte[] posterPath = ImageDownloadClass.getImage(getMovieImageURL + movie.getString("poster_path"));
        return MovieListItem.builder()
                .movieId(movie.getLong("id"))
                .title(movie.getString("title"))
                .releaseDate(Date.valueOf(movie.getString("release_date")))
                .posterImage(posterPath)
                .voteAverage(0.0)
                .voteCount(0L)
                .build();
    }
}