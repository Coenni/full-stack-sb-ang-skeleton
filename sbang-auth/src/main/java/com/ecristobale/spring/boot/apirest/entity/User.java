package com.ecristobale.spring.boot.apirest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String username;

	@Column
	private String firstname;
	
	@Column
	private String lastname;

	private String password;

	private Boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER) // TODO fix n+1 query issue
	@JoinTable(
			name = "authorities",
			joinColumns = {@JoinColumn(name = "username", referencedColumnName = "username")},
			inverseJoinColumns = {@JoinColumn(name = "authority", referencedColumnName = "authority")})
	private List<Role> roles;

}
