package com.blogwithvikram.blog.services;

import java.util.List;

import com.blogwithvikram.blog.payload.CategoryDto;

public interface CategoryService {

	// create
	public CategoryDto createCategory(CategoryDto categoryDto);

	// update
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	// delete
	public void deleteCategory(Integer categoryId);

	// get all categories
	public List<CategoryDto> getAllCategories();

	// get category by Id
	public CategoryDto getCategory(Integer categoryId);
}
