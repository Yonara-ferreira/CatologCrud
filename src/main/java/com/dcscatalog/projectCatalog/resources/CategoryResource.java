package com.dcscatalog.projectCatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcscatalog.projectCatalog.entities.Category;
import com.dcscatalog.projectCatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping(value = "/find-categories")
	public ResponseEntity<List<Category>> findAll(){
		return ResponseEntity.ok().body(service.findAllCategories());
	}
	
	

}