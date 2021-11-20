package com.example.MovieRank.Controllers;

import com.example.MovieRank.DTO.Comment.Request.AddCommentRequest;
import com.example.MovieRank.DTO.Comment.Request.AddLikeRequest;
import com.example.MovieRank.DTO.Comment.Request.AddUnlikeRequest;
import com.example.MovieRank.DTO.Comment.Request.EditCommentRequest;
import com.example.MovieRank.DTO.Comment.Response.CommentListItem;
import com.example.MovieRank.DTO.MessageResponse;
import com.example.MovieRank.Services.Comment.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) { this.commentService = commentService; }

    @PostMapping("add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> addComment(@RequestBody AddCommentRequest addCommentRequest) {

        MessageResponse messageResponse = commentService.addComment(addCommentRequest);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("edit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> editComment(@RequestBody EditCommentRequest editCommentRequest) {

        MessageResponse messageResponse = commentService.editComment(editCommentRequest);
        return ResponseEntity.ok(messageResponse);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable(name = "id") Long commentId) {

        MessageResponse messageResponse = commentService.deleteComment(commentId);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("like")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> addLike(@RequestBody AddLikeRequest addLikeRequest) {

        MessageResponse messageResponse = commentService.addLike(addLikeRequest);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("unlike")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> addUnlike(@RequestBody AddUnlikeRequest addUnlikeRequest) {

        MessageResponse messageResponse = commentService.addUnlike(addUnlikeRequest);
        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping("get/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CommentListItem>> getComments(@PathVariable(name = "id") Long movieId) {

        List<CommentListItem> commentListItem = commentService.getComments(movieId);
        return ResponseEntity.ok(commentListItem);
    }
}
