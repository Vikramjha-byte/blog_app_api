package com.blogwithvikram.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogwithvikram.blog.payload.ApiResponse;
import com.blogwithvikram.blog.payload.CategoryDto;
import com.blogwithvikram.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	//create
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto){
		CategoryDto createCategoryDto=categoryService.createCategory(dto);
		return new ResponseEntity<CategoryDto>(createCategoryDto,HttpStatus.CREATED);
	}
	//update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto dto,@PathVariable("catId") Integer catId){
		CategoryDto updateCategoryDto=categoryService.updateCategory(dto,catId); 
		return new ResponseEntity<CategoryDto>(updateCategoryDto,HttpStatus.OK);
	}
	//delete
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") Integer catId){
categoryService.deleteCategory(catId); 
		return new ResponseEntity<ApiResponse
				>(new ApiResponse("Category is deleted successfully!", true),HttpStatus.OK);
	}
	
	//getAllCategories
	@GetMapping("/")
	public ResponseEntity<?> getAllCategories(){
List<CategoryDto> allCategories = categoryService.getAllCategories(); 
		return new ResponseEntity<>(allCategories,HttpStatus.OK);
	}
	
	//getCategoryById
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer catId){
CategoryDto category = categoryService.getCategory(catId); 
		return new ResponseEntity<>(category,HttpStatus.OK);
	}
}
