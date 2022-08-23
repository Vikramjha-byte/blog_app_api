package com.blogwithvikram.blog.payload;

import com.blogwithvikram.blog.entities.User;

public class CommentDto {

	private Integer commentId;

	private String content;

	private UserDto user;

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}
