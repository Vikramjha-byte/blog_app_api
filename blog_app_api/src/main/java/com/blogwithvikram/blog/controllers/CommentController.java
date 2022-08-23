package com.blogwithvikram.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogwithvikram.blog.payload.ApiResponse;
import com.blogwithvikram.blog.payload.CommentDto;
import com.blogwithvikram.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	// create comment
	@PostMapping("user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,
			@PathVariable("postId") Integer postId,@PathVariable("userId") Integer userId) {
		CommentDto createComment = commentService.createComment(comment, postId,userId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}

	// Delete comment
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId) {
		commentService.deleteComment(commentId);
		return new ResponseEntity<>(new ApiResponse("Comment deleted successfully!", true), HttpStatus.OK);
	}
}
