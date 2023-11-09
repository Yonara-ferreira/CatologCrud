package com.dcscatalog.projectCatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dcscatalog.projectCatalog.dto.CategoryDTO;
import com.dcscatalog.projectCatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping(value = "/find-categories")
	public ResponseEntity<Page<CategoryDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
													 @RequestParam(value = "linesPorPage", defaultValue = "10") Integer linesPorPage,
													 @RequestParam(value = "direction", defaultValue = "ASC") String direction,
													 @RequestParam(value = "orderBy", defaultValue = "name") String orderBy){
		
		PageRequest pageRequest = PageRequest.of(page, linesPorPage);

		Page<CategoryDTO> list = service.findAllCategories(pageRequest);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/find-categories/{idCategory}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable(value = "idCategory") Long idCategory){
		CategoryDTO dto = service.findByIdCategory(idCategory);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(value = "/newCategory")
	public ResponseEntity<CategoryDTO> insertNewCategory(@RequestBody  CategoryDTO dto){
		dto = service.insertNewCategory(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody  CategoryDTO dto){
		dto = service.updateCategory(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id){
		service.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}

}
