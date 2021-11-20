package com.example.MovieRank.Services.Comment;

import com.example.MovieRank.DTO.Comment.Request.AddCommentRequest;
import com.example.MovieRank.DTO.Comment.Request.AddLikeRequest;
import com.example.MovieRank.DTO.Comment.Request.AddUnlikeRequest;
import com.example.MovieRank.DTO.Comment.Request.EditCommentRequest;
import com.example.MovieRank.DTO.Comment.Response.CommentListItem;
import com.example.MovieRank.DTO.MessageResponse;
import com.example.MovieRank.Entities.Comment;
import com.example.MovieRank.Entities.User;
import com.example.MovieRank.Exceptions.LikeAlreadyAddedException;
import com.example.MovieRank.Repositories.CommentRepository;
import com.example.MovieRank.Repositories.UserRepository;
import com.example.MovieRank.Services.Comment.GetActualTime.GetActualTimeClass;
import com.example.MovieRank.Services._DatabaseAnalysis.CommentAnalysis;
import com.example.MovieRank.Services._DatabaseAnalysis.UserAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    Logger logger = LoggerFactory.getLogger(CommentService.class);

    public MessageResponse addComment(AddCommentRequest addCommentRequest) {

        User user = UserAnalysis.findUserByUserId(userRepository, addCommentRequest.getUserId());

        Comment comment = Comment.builder()
                .movieId(addCommentRequest.getMovieId())
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .addingDate(Date.valueOf(LocalDate.now()))
                .addingTime(GetActualTimeClass.getActualTime())
                .info(addCommentRequest.getInfo())
                .likeNumber(0L)
                .unlikeNumber(0L)
                .build();

        commentRepository.save(comment);

        logger.info("Comment has been added: " + comment.getAddingDate() + " " + comment.getAddingTime());
        return new MessageResponse("Komentarz został dodany");
    }

    public MessageResponse editComment(EditCommentRequest editCommentRequest) {

        Comment comment = CommentAnalysis.findCommentByCommentId(commentRepository, editCommentRequest.getCommentId());

        comment.setInfo(editCommentRequest.getInfo());
        commentRepository.save(comment);

        logger.info("Comment has been edited");
        return new MessageResponse("Komentarz został zmieniony");
    }

    public MessageResponse deleteComment(Long commentId) {

        Comment comment = CommentAnalysis.findCommentByCommentId(commentRepository, commentId);
        commentRepository.delete(comment);

        logger.info("Comment has been deleted");
        return new MessageResponse("Komentarz został usunięty");
    }

    public MessageResponse addLike(AddLikeRequest addLikeRequest) {

        User user = UserAnalysis.findUserByUserId(userRepository, addLikeRequest.getUserId());
        Comment comment = CommentAnalysis.findCommentByCommentId(commentRepository, addLikeRequest.getCommentId());

        comment.getUsersLike().forEach(userId -> {
            if (userId.equals(addLikeRequest.getUserId())) {
                logger.error("Like has already been added");
                throw new LikeAlreadyAddedException("Like został już dodany");
            }
        });

        comment.getUsersUnlike().forEach(userId -> {
            if (userId.equals(addLikeRequest.getUserId())) {
                comment.getUsersUnlike().remove(userId);
                comment.setUnlikeNumber(comment.getUnlikeNumber() - 1);
                logger.info("Unlike has been removed. Unlike Number = " + comment.getUnlikeNumber());

            }
        });

        comment.getUsersLike().add(addLikeRequest.getUserId());
        comment.setLikeNumber(comment.getLikeNumber() + 1);

        commentRepository.save(comment);
        logger.info("Like has been added. Like Number = " + comment.getLikeNumber());
        return new MessageResponse("Like został dodany");
    }

    public MessageResponse addUnlike(AddUnlikeRequest addUnlikeRequest) {

        User user = UserAnalysis.findUserByUserId(userRepository, addUnlikeRequest.getUserId());
        Comment comment = CommentAnalysis.findCommentByCommentId(commentRepository, addUnlikeRequest.getCommentId());

        comment.getUsersUnlike().forEach(userId -> {
            if (userId.equals(addUnlikeRequest.getUserId())) {
                logger.error("Unlike has already been added");
                throw new LikeAlreadyAddedException("Unlike został już dodany");
            }
        });

        comment.getUsersLike().forEach(userId -> {
            if (userId.equals(addUnlikeRequest.getUserId())) {
                comment.getUsersLike().remove(userId);
                comment.setLikeNumber(comment.getLikeNumber() - 1);
                logger.info("Like has been removed. Like Number = " + comment.getLikeNumber());
            }
        });

        comment.getUsersUnlike().add(addUnlikeRequest.getUserId());
        comment.setUnlikeNumber(comment.getUnlikeNumber() + 1);

        commentRepository.save(comment);
        logger.info("Unlike has been added. Unlike Number = " + comment.getUnlikeNumber());
        return new MessageResponse("Unlike został dodany");
    }

    public List<CommentListItem> getComments(Long movieId) {

        return CommentAnalysis.findAllByMovieId(commentRepository, movieId);
    }
}
