package com.adsh.personalProjects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@RestController //告诉编译器这是一个REST API
public class PersonalProjectsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalProjectsApplication.class, args);
	}

//	@GetMapping("/")
//	public String apiIndex(){
//		return "Hello";
//	}

}
