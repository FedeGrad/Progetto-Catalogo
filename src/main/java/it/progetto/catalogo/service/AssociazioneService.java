package it.progetto.catalogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.progetto.catalogo.model.Autore;
import it.progetto.catalogo.model.Categoria;
import it.progetto.catalogo.model.Libro;
import it.progetto.catalogo.repository.AutoreRepository;
import it.progetto.catalogo.repository.CategoriaRepository;
import it.progetto.catalogo.repository.LibroRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AssociazioneService {
	@Autowired
	LibroRepository libRepo;
	@Autowired
	AutoreRepository autoRepo;
	@Autowired
	CategoriaRepository catRepo;

	public boolean associaLibroAutore(String titolo, String cognome) {
		if (libRepo.existsByTitolo(titolo) || !autoRepo.existsBycognome(cognome)) {
			log.info("restituisco false - esco da associaLibroAutore");
			return false;
		} else {
			Libro lib = new Libro();
			// Libro lib = libRepo.findByTitolo(titolo);
			Autore aut = (Autore) autoRepo.findByCognome(cognome);
			lib.getAutori().add(aut);
			aut.getLibri().add(lib);
			log.info(lib.toString() + " " + aut.toString());
			autoRepo.save(aut);
			libRepo.save(lib);
			return true;
		}
	}

	public boolean associaLibroCategoria(String titolo, String tipo) {
		if (!libRepo.existsByTitolo(titolo) || !catRepo.existsByTipo(tipo)) {
			return false;
		} else {
			Libro lib = libRepo.findByTitolo(titolo);
			Categoria cat = catRepo.findByTipo(tipo);
			lib.getCategorie().add(cat);
			cat.getLibri().add(lib);
			libRepo.save(lib);
			return true;
		}
	}

}
