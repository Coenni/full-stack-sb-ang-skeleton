package com.ecristobale.spring.boot.apirest.models.dao;

import java.util.List;

import com.ecristobale.spring.boot.apirest.models.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecristobale.spring.boot.apirest.models.entity.Region;

public interface IClientDao extends JpaRepository<Client, Long> {

	@Query("from Region")
	public List<Region> findAllRegions();
}
