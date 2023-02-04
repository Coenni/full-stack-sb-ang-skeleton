package com.ecristobale.spring.boot.apirest.models.services;

import java.util.List;

import com.ecristobale.spring.boot.apirest.models.entity.Client;
import com.ecristobale.spring.boot.apirest.models.entity.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecristobale.spring.boot.apirest.models.dao.IClientDao;
import com.ecristobale.spring.boot.apirest.models.dao.IInvoiceDao;
import com.ecristobale.spring.boot.apirest.models.dao.IProductDao;
import com.ecristobale.spring.boot.apirest.models.entity.Product;
import com.ecristobale.spring.boot.apirest.models.entity.Region;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	IClientDao clientDao;
	
	@Autowired
	IInvoiceDao facturaDao;
	
	@Autowired
	IProductDao productoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		return (List<Client>) clientDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Client> findAll(Pageable pageable) {
		return clientDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Client findById(Long id) {
		return clientDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Client save(Client client) {
		return clientDao.save(client);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clientDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegions() {
		return clientDao.findAllRegions();
	}

	@Override
	@Transactional(readOnly = true)
	public Invoice findInvoiceById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Invoice save(Invoice invoice) {
		return facturaDao.save(invoice);
	}

	@Override
	@Transactional
	public void deleteInvoice(Long id) {
		facturaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findProductByName(String term) {
		return productoDao.findByNameContainingIgnoreCase(term);
	}

}
