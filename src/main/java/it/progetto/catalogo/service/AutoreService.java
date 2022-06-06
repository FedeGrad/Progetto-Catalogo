package it.progetto.catalogo.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import it.progetto.catalogo.dto.AutoreDTO;
import it.progetto.catalogo.dto.CategoriaDTO;
import it.progetto.catalogo.exception.ElementAlreadyPresentException;
import it.progetto.catalogo.model.Autore;
import it.progetto.catalogo.model.Categoria;
import it.progetto.catalogo.model.Libro;
import it.progetto.catalogo.repository.AutoreRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AutoreService {

	@Autowired
	AutoreRepository autoreRepo;

	public List<Autore> elencoAutori() {
		return (List<Autore>) autoreRepo.findAll();
	}

	public void inserisciAutore(AutoreDTO autore) throws ElementAlreadyPresentException {
		Autore aut = new Autore();
		if (!autoreRepo.existsBynome(autore.getNome())) {
			BeanUtils.copyProperties(autore, aut);
			autoreRepo.save(aut);
			log.info("Autore " + autore.getCognome() + " salvato");
		} else {
			throw new ElementAlreadyPresentException("Autore " + autore.getCognome() + " già presente nel sistema");
		}
	}

	public void eliminaAutoreByCognome(String cognome) {
		if (autoreRepo.existsBycognome(cognome)) {
			autoreRepo.deleteByCognome(cognome);
			log.info("Autore " + cognome + " eliminato");
		} else {
			throw new NotFoundException("Autore " + cognome + " non trovato");
		}
	}

	public void modificaAutore(AutoreDTO autore) {
		if (autoreRepo.existsBycognome(autore.getCognome())) {
			Autore aut = autoreRepo.findByCognome(autore.getCognome());
			BeanUtils.copyProperties(autore, aut);
			autoreRepo.save(aut);
			log.info("Autore " + autore.getCognome() + " eliminato");
		} else {
			throw new NotFoundException("Autore " + autore.getCognome() + " non trovato");
		}
	}

}
