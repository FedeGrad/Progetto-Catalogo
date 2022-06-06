package it.progetto.catalogo.confing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import it.progetto.catalogo.model.Autore;
import it.progetto.catalogo.model.Categoria;
import it.progetto.catalogo.model.Libro;
import it.progetto.catalogo.repository.AutoreRepository;
import it.progetto.catalogo.repository.CategoriaRepository;
import it.progetto.catalogo.repository.LibroRepository;

@Configuration
@OpenAPIDefinition
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer")
public class CatalogoConfing {

	@Autowired
	LibroRepository libRepo;
	@Autowired
	AutoreRepository autoRepo;
	@Autowired
	CategoriaRepository catRepo;


	@Bean
	public Libro divinaCommedia() {
		Libro lib = new Libro();
		lib.setTitolo("Divina Commedia");
		lib.setPrezzo(15.0);
		lib.setAnnoDiProduzione(LocalDate.parse("1400-02-02"));
		libRepo.save(lib);
		Autore aut = new Autore();
		aut.setNome("Dante");
		aut.setCognome("Alighieri");
		Categoria cat = new Categoria();
		cat.setTipo("fantasy");
		lib.getAutori().add(aut);
		aut.getLibri().add(lib);
		lib.getCategorie().add(cat);
		cat.getLibri().add(lib);
		//				autoRepo.save(aut);
		//				catRepo.save(cat);
		return lib;
	}

	@Bean
	public Libro malavoglia() {
		Libro lib = new Libro();
		lib.setTitolo("Malavoglia");
		lib.setPrezzo(15.0);
		lib.setAnnoDiProduzione(LocalDate.parse("1800-05-15"));
		libRepo.save(lib);
		Autore aut = new Autore();
		aut.setNome("Giovanni");
		aut.setCognome("Verga");
		Categoria cat = new Categoria();
		cat.setTipo("romanzo");
		lib.getAutori().add(aut);
		aut.getLibri().add(lib);
		lib.getCategorie().add(cat);
		cat.getLibri().add(lib);
		//				autoRepo.save(aut);
		//				catRepo.save(cat);
		return lib;
	}

	@Bean
	public Libro misery() {
		Libro lib = new Libro();
		lib.setTitolo("Mysery non deve morire");
		lib.setPrezzo(18.0);
		lib.setAnnoDiProduzione(LocalDate.parse("1999-07-20"));
		libRepo.save(lib);
		Autore aut = new Autore();
		aut.setNome("Stephen");
		aut.setCognome("King");
		Categoria cat = new Categoria();
		cat.setTipo("horror");
		lib.getAutori().add(aut);
		aut.getLibri().add(lib);
		lib.getCategorie().add(cat);
		cat.getLibri().add(lib);
		//				autoRepo.save(aut);
		//				catRepo.save(cat);
		return lib;
	}

}
