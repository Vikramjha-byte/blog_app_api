package com.blogwithvikram.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogwithvikram.blog.dao.CommentDao;
import com.blogwithvikram.blog.dao.PostDao;
import com.blogwithvikram.blog.dao.UserDao;
import com.blogwithvikram.blog.entities.Comment;
import com.blogwithvikram.blog.entities.Post;
import com.blogwithvikram.blog.entities.User;
import com.blogwithvikram.blog.exceptions.ResourceNotFoundException;
import com.blogwithvikram.blog.payload.CommentDto;
import com.blogwithvikram.blog.services.CommentService;

@Service
public class CommentServiceimpl implements CommentService {

	@Autowired
	private PostDao postDao;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private UserDao userDao;
	@Autowired
	private ModelMapper mapper;

	// create
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId) {
		Post post = postDao.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		
		User user = userDao.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));
		Comment comment = mapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment = commentDao.save(comment);
		return mapper.map(savedComment, CommentDto.class);
	}

//delete
	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentDao.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
		commentDao.delete(comment);
	}
//update

	// get all comment by post

	// get all comment

	// get all comment by user
}
