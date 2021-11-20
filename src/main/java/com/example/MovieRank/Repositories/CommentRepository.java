package com.example.MovieRank.Repositories;

import com.example.MovieRank.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentByCommentId(Long commentId);

    List<Comment> findAllByMovieId(Long movieId);
}
