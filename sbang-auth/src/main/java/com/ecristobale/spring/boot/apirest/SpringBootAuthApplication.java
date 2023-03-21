package com.ecristobale.spring.boot.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootAuthApplication {// implements CommandLineRunner{

//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootAuthApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		String pwd = "12345";
//		for(int i=0; i< 4; i++) {
//			String pwdBCrypt = passwordEncoder.encode(pwd);
//			System.out.println(pwdBCrypt);
//		}
//	}

}
