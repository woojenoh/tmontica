package com.internship.tmontica;

import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.security.JwtServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TmonticaApplication {
	@Bean
	public ModelMapper modelMapper(){	return new ModelMapper(); }


	@Bean
	public JwtService jwtService() {
		return new JwtServiceImpl();
	}

	public static void main(String[] args) {
		SpringApplication.run(TmonticaApplication.class, args);
	}

}
