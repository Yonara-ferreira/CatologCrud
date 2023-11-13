package com.dcscatalog.projectCatalog.services;

import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcscatalog.projectCatalog.dto.CategoryDTO;
import com.dcscatalog.projectCatalog.dto.ProductDTO;
import com.dcscatalog.projectCatalog.entities.Category;
import com.dcscatalog.projectCatalog.entities.Product;
import com.dcscatalog.projectCatalog.repositories.CategoryRepository;
import com.dcscatalog.projectCatalog.repositories.ProductRepository;
import com.dcscatalog.projectCatalog.services.exceptions.DatabaseException;
import com.dcscatalog.projectCatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllCategories(PageRequest pageRequest) {
		Page<Product> products = repository.findAll(pageRequest);
		return products.map(product -> new ProductDTO(product));
	}

	@Transactional(readOnly = true)
	public ProductDTO findByIdProduct(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		Hibernate.initialize(entity.getCategories());
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insertNewProduct(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO updateProduct(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("id not found " + id);
		}
	}


	public void deleteProduct(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("id not found " + id);
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}

	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		
		entity.getCategories().clear();
		for(CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getOne(catDto.getId());
			entity.getCategories().add(category);
		}
		
	}

}
