package com.blogwithvikram.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.stereotype.Service;

import com.blogwithvikram.blog.dao.CategoryDao;
import com.blogwithvikram.blog.dao.PostDao;
import com.blogwithvikram.blog.dao.UserDao;
import com.blogwithvikram.blog.entities.Category;
import com.blogwithvikram.blog.entities.Post;
import com.blogwithvikram.blog.entities.User;
import com.blogwithvikram.blog.exceptions.ResourceNotFoundException;
import com.blogwithvikram.blog.payload.PostDto;
import com.blogwithvikram.blog.payload.Postresponse;
import com.blogwithvikram.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao dao;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private Postresponse postresponse;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = userDao.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		Category category = categoryDao.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		Post post = mapper.map(postDto, Post.class);
		post.setImageName("deafult.png");
		post.setPostDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = dao.save(post);
		return mapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = dao.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		post.setPostTitle(postDto.getPostTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = dao.save(post);
		return mapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = dao.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		dao.delete(post);
	}

	@Override
	public List<PostDto> getAllPost() {
		List<Post> allPosts = dao.findAll();
		List<PostDto> postDtos = allPosts.stream().map((post) -> mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = dao.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		return mapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cat = categoryDao.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = dao.findByCategory(cat);
		List<PostDto> postDtos = posts.stream().map((post) -> mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = userDao.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		List<Post> posts = dao.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = dao.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map((post)-> mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public Postresponse getPostPerPage(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = dao.findAll(pageable);
		List<Post> content = pagePost.getContent();
		List<PostDto> postDtos = content.stream().map((post) -> mapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		postresponse.setContent(postDtos);
		postresponse.setPageNumber(pagePost.getNumber());
		postresponse.setTotalElements(pagePost.getTotalElements());
		postresponse.setPageSize(pagePost.getSize());
		postresponse.setTotalPages(pagePost.getTotalPages());
		postresponse.setIsLastPage(pagePost.isLast());
		return postresponse;
	}

	@Override
	public Postresponse getPostPerPageCategoryWise(Integer pageNumber, Integer pageSize, Integer categoryId,
			String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Category cat = categoryDao.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pages = dao.findByCategory(cat, pageable);
		List<Post> content = pages.getContent();
		List<PostDto> postDtos = content.stream().map((post) -> mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		postresponse.setContent(postDtos);
		postresponse.setPageNumber(pages.getNumber());
		postresponse.setTotalElements(pages.getTotalElements());
		postresponse.setPageSize(pages.getSize());
		postresponse.setTotalPages(pages.getTotalPages());
		postresponse.setIsLastPage(pages.isLast());
		return postresponse;
	}

	@Override
	public Postresponse getPostByUserPagination(Integer pageNumber, Integer pageSize, Integer userId, String sortBy,
			String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		User user = userDao.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pages = dao.findByUser(user, pageable);
		List<Post> content = pages.getContent();
		List<PostDto> postDtos = content.stream().map((post) -> mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		postresponse.setContent(postDtos);
		postresponse.setPageNumber(pages.getNumber());
		postresponse.setTotalElements(pages.getTotalElements());
		postresponse.setPageSize(pages.getSize());
		postresponse.setTotalPages(pages.getTotalPages());
		postresponse.setIsLastPage(pages.isLast());
		return postresponse;
	}

}
