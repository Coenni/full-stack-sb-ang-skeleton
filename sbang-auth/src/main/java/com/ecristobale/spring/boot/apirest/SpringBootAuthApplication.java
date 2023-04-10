package com.ecristobale.spring.boot.apirest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringBootAuthApplication  implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootAuthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String pwd = "pass";
		for(int i=0; i< 4; i++) {
			String pwdBCrypt = passwordEncoder.encode(pwd);
			System.out.println(pwdBCrypt);
		}
	}

}
