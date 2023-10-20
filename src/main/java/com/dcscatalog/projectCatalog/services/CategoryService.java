package com.dcscatalog.projectCatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcscatalog.projectCatalog.entities.Category;
import com.dcscatalog.projectCatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAllCategories(){
		List<Category> categories = repository.findAll();
		return categories.stream().map(category -> new Category()).toList();
	}
	
	

}