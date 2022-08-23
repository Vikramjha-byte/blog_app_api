package com.blogwithvikram.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogwithvikram.blog.entities.Comment;

public interface CommentDao extends JpaRepository<Comment, Integer>{

}
