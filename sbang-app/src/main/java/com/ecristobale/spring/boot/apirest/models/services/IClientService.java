package com.ecristobale.spring.boot.apirest.models.services;

import java.util.List;

import com.ecristobale.spring.boot.apirest.models.entity.Client;
import com.ecristobale.spring.boot.apirest.models.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecristobale.spring.boot.apirest.models.entity.Product;
import com.ecristobale.spring.boot.apirest.models.entity.Region;

public interface IClientService {

	public List<Client> findAll();

	public Page<Client> findAll(Pageable pageable);
	
	public Client findById(Long id);
	
	public Client save(Client client);
	
	public void delete(Long id);
	
	public List<Region> findAllRegions();
	
	public Invoice findInvoiceById(Long id);
	
	public Invoice save(Invoice invoice);
	
	public void deleteInvoice(Long id);
	
	public List<Product> findProductByName(String term);
}
