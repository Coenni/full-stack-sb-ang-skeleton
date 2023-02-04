package com.ecristobale.spring.boot.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecristobale.spring.boot.apirest.models.entity.ProfileImg;

public interface ProfileImgDao extends JpaRepository<ProfileImg, Long> {
	
	Long deleteByFilename(String filename);
	
	ProfileImg findByFilename(String filename);
}
