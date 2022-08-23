package com.blogwithvikram.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogwithvikram.blog.entities.Category;

public interface CategoryDao extends JpaRepository<Category, Integer> {

}
