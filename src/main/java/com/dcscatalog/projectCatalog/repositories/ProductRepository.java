package com.dcscatalog.projectCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dcscatalog.projectCatalog.entities.Category;
import com.dcscatalog.projectCatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
