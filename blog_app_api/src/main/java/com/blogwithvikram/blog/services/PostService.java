package com.blogwithvikram.blog.services;

import java.util.List;

import com.blogwithvikram.blog.entities.Post;
import com.blogwithvikram.blog.payload.PostDto;
import com.blogwithvikram.blog.payload.Postresponse;

public interface PostService {

	// Create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// update
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete

	void deletePost(Integer postId);

	// getPostByPage

	Postresponse getPostPerPage(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

	//Pagination category
	
	Postresponse getPostPerPageCategoryWise(Integer pageNumber,Integer pageSize,Integer categoryId,String sortBy,String sortDir);
	// getAllPost

	List<PostDto> getAllPost();

	// getPostById

	PostDto getPostById(Integer postId);

	// get Post By category

	List<PostDto> getPostByCategory(Integer categoryId);

	// getPost By User

	List<PostDto> getPostByUser(Integer userId);

	// Search Post

	List<PostDto> searchPost(String keyword);

	Postresponse getPostByUserPagination(Integer pageNumber, Integer pageSize, Integer userId,String sortBy,String sortDir);

}
