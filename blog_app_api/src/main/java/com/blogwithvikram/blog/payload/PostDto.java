package com.blogwithvikram.blog.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blogwithvikram.blog.entities.Category;
import com.blogwithvikram.blog.entities.Comment;
import com.blogwithvikram.blog.entities.User;

public class PostDto {

	private Integer postId;
	private String postTitle;
	private String content;
	private String ImageName;
	private Date postDate;
	private Category category;
	private UserDto user;
	private List<CommentDto> comments= new ArrayList<CommentDto>();
	
	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getImageName() {
		return ImageName;
	}

	public void setImageName(String imageName) {
		ImageName = imageName;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<CommentDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}

	

}
