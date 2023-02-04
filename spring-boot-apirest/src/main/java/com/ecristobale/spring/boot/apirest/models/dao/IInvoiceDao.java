package com.ecristobale.spring.boot.apirest.models.dao;

import com.ecristobale.spring.boot.apirest.models.entity.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface IInvoiceDao extends CrudRepository<Invoice, Long> {

}
