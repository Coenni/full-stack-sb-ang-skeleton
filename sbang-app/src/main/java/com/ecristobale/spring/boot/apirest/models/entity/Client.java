package com.ecristobale.spring.boot.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "can't be empty")
	@Size(min = 4, max = 12, message = "the size must be between 4 and 12 characters")
	@Column(nullable = false)
	private String name;

	@NotEmpty(message = "can't be empty")
	@Column(nullable = false)
	private String lastname;

	@NotEmpty(message = "can't be empty")
	@Email(message = "do not have a valid format")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotNull(message = "can't be empty")
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	private String photo;
	
	@NotNull(message = "can't be empty")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Region region;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "client"}, allowSetters = true)
	private List<Invoice> invoices;

	private static final long serialVersionUID = 1L;
}
