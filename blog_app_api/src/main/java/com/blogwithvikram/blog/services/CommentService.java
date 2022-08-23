package com.blogwithvikram.blog.services;

import com.blogwithvikram.blog.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
	void deleteComment(Integer commentId);
}
