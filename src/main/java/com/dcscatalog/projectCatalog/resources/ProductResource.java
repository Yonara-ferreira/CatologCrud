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

import com.dcscatalog.projectCatalog.dto.ProductDTO;
import com.dcscatalog.projectCatalog.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	@GetMapping(value = "/find-products")
	public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
													 @RequestParam(value = "linesPorPage", defaultValue = "10") Integer linesPorPage,
													 @RequestParam(value = "direction", defaultValue = "ASC") String direction,
													 @RequestParam(value = "orderBy", defaultValue = "name") String orderBy){
		
		PageRequest pageRequest = PageRequest.of(page, linesPorPage);

		Page<ProductDTO> list = service.findAllCategories(pageRequest);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/find-products/{idProduct}")
	public ResponseEntity<ProductDTO> findById(@PathVariable(value = "idProduct") Long idProduct){
		ProductDTO dto = service.findByIdProduct(idProduct);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(value = "/newProduct")
	public ResponseEntity<ProductDTO> insertNewProduct(@RequestBody  ProductDTO dto){
		dto = service.insertNewProduct(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody  ProductDTO dto){
		dto = service.updateProduct(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id){
		service.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

}
