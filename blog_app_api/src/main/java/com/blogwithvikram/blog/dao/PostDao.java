package com.blogwithvikram.blog.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogwithvikram.blog.entities.Category;
import com.blogwithvikram.blog.entities.Post;
import com.blogwithvikram.blog.entities.User;

public interface PostDao extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	Page<Post> findByCategory(Category category, Pageable pageable);
	Page<Post> findByUser(User user, Pageable pageable);
	
	@Query("select p from Post p where p.postTitle like :key")
	List<Post> searchByTitle(@Param("key") String postTitle);
}
