package com.blogwithvikram.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogwithvikram.blog.config.AppConstants;
import com.blogwithvikram.blog.payload.ApiResponse;
import com.blogwithvikram.blog.payload.PostDto;
import com.blogwithvikram.blog.payload.Postresponse;
import com.blogwithvikram.blog.services.FileService;
import com.blogwithvikram.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId,
			@PathVariable("categoryId") Integer categoryId) {
		PostDto createPost = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {

		PostDto updatePost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	// Delete
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable("postId") Integer postId) {
		postService.deletePost(postId);
		return new ApiResponse("Post Deleted Successfully!", true);
	}

	// getAllPost
	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>> getAllPost() {
		List<PostDto> allPost = postService.getAllPost();
		return new ResponseEntity<List<PostDto>>(allPost, HttpStatus.OK);
	}

	// getPost details by Id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getpostById(@PathVariable("postId") Integer postId) {
		PostDto postById = postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
	}

	// getPostByUser
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable("userId") Integer userId) {
		List<PostDto> postByUser = postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postByUser, HttpStatus.OK);
	}

	// getPostBycategory
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId) {
		List<PostDto> postByCategory = postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postByCategory, HttpStatus.OK);
	}

	// pagination category

	@GetMapping("/categories/{categoryId}/posts")
	public ResponseEntity<?> getPostByCategoryPagination(@PathVariable("categoryId") Integer categoryId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		Postresponse postPerPageCategoryWise = postService.getPostPerPageCategoryWise(pageNumber, pageSize, categoryId,
				sortBy, sortDir);
		return new ResponseEntity<>(postPerPageCategoryWise, HttpStatus.OK);
	}

	// pagination user
	@GetMapping("/users/{userId}/posts")
	public ResponseEntity<?> getPostByUserPagination(@PathVariable("userId") Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		Postresponse postPerPageUserWise = postService.getPostByUserPagination(pageNumber, pageSize, userId, sortBy,
				sortDir);
		return new ResponseEntity<>(postPerPageUserWise, HttpStatus.OK);
	}

	// getPostPerPage

	@GetMapping("/allPosts")
	public ResponseEntity<Postresponse> getPostPerPage(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		Postresponse postPerPage = postService.getPostPerPage(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(postPerPage, HttpStatus.OK);
	}

	// Search

	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> searchPost = postService.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(searchPost, HttpStatus.OK);
	}

	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId) throws IOException {
		PostDto post = postService.getPostById(postId);
		String fileName = fileService.uploadImage(path, image);
		System.out.println(fileName);
		post.setImageName(fileName);
		PostDto updatePost = postService.updatePost(post, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}
	
	//Serving post Image
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void serverImage(
			@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException{
		InputStream inputStream=fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, response.getOutputStream());
	}
}
