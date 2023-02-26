package com.ecristobale.spring.boot.apirest.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecristobale.spring.boot.apirest.models.entity.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

//	@Query("select p from product p where p.name like %?1%")
//	public List<product> findByname(String term);

//	public List<product> findBynameStartingWithIgnoreCase(String term);

	public List<Product> findByNameContainingIgnoreCase(String term);
}
