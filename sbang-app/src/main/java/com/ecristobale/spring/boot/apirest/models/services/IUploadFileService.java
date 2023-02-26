package com.ecristobale.spring.boot.apirest.models.services;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.ecristobale.spring.boot.apirest.models.entity.ProfileImg;

public interface IUploadFileService {

	public ProfileImg loadFile(String filename) throws MalformedURLException;
	public String uploadFile(MultipartFile file) throws IOException;
	public boolean deleteFile(String filename);
//	public Path getPath(String filename);
	public Resource loadFileDefaultImage(String filename) throws MalformedURLException;
}
