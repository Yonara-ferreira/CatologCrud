package com.dcscatalog.projectCatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcscatalog.projectCatalog.dto.CategoryDTO;
import com.dcscatalog.projectCatalog.entities.Category;
import com.dcscatalog.projectCatalog.repositories.CategoryRepository;
import com.dcscatalog.projectCatalog.services.exceptions.DatabaseException;
import com.dcscatalog.projectCatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAllCategories(){
		List<Category> categories = repository.findAll();
		return categories.stream().map(category -> new CategoryDTO(category)).toList();
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findByIdCategory(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
	}
	@Transactional
	public CategoryDTO insertNewCategory(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		
		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);	
			return new CategoryDTO(entity);
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("id not found " + id );
		}
	}

	public void deleteCategory(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("id not found " + id);
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}

	}
	
	
	
	
		

}
