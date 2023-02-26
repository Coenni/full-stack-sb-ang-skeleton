package com.ecristobale.spring.boot.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecristobale.spring.boot.apirest.models.entity.Invoice;
import com.ecristobale.spring.boot.apirest.models.entity.Product;
import com.ecristobale.spring.boot.apirest.models.services.IClientService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class InvoiceRestController {

	@Autowired
	private IClientService clientService;
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/invoices/{id}")
	@ResponseStatus(code=HttpStatus.OK)
	public Invoice show(@PathVariable("id") Long id) {
		return clientService.findInvoiceById(id);
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/invoices/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		clientService.deleteInvoice(id);
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/invoices/product-filter/{term}")
	@ResponseStatus(code=HttpStatus.OK)
	public List<Product> productFilter(@PathVariable("term") String term) {
		return clientService.findProductByName(term);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/invoices")
	@ResponseStatus(code=HttpStatus.CREATED)
	public Invoice createInvoice(@RequestBody Invoice invoice) {
		return clientService.save(invoice);
	}
}
