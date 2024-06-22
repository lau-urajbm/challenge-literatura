package com.bmlgold.challenge_literatura;

import com.bmlgold.challenge_literatura.principal.Principal;
import com.bmlgold.challenge_literatura.repository.AutoresRepository;
import com.bmlgold.challenge_literatura.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraturaApplication implements CommandLineRunner {
	@Autowired
	private AutoresRepository autoresRepository;
	@Autowired
	private LibrosRepository librosRepository;


	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraturaApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception{
		Principal principal = new Principal(librosRepository, autoresRepository);
		principal.mostrarMenu();

	}




}
