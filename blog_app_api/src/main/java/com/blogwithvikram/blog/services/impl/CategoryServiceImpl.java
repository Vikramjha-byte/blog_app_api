package com.blogwithvikram.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogwithvikram.blog.dao.CategoryDao;
import com.blogwithvikram.blog.entities.Category;
import com.blogwithvikram.blog.exceptions.ResourceNotFoundException;
import com.blogwithvikram.blog.payload.CategoryDto;
import com.blogwithvikram.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = mapper.map(categoryDto, Category.class);
		Category addedCategory = categoryDao.save(category);
		return mapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = categoryDao.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory = categoryDao.save(cat);
		return mapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = categoryDao.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		categoryDao.delete(cat);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = categoryDao.findAll();
		List<CategoryDto> collectedCategories = categories.stream()
				.map((category) -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return collectedCategories;
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = categoryDao.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		return mapper.map(cat, CategoryDto.class);
	}

}
