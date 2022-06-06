package it.progetto.catalogo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import it.progetto.catalogo.dto.LibroDTO;
import it.progetto.catalogo.dto.LibroDTO2;
import it.progetto.catalogo.dto.LibroDTOargs;
import it.progetto.catalogo.exception.ElementAlreadyPresentException;
import it.progetto.catalogo.model.Autore;
import it.progetto.catalogo.model.Categoria;
import it.progetto.catalogo.model.Libro;
import it.progetto.catalogo.repository.AutoreRepository;
import it.progetto.catalogo.repository.CategoriaRepository;
import it.progetto.catalogo.repository.LibroRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LibroService {

	@Autowired
	LibroRepository libRepo;
	@Autowired
	AutoreRepository autoRepo;
	@Autowired
	CategoriaRepository catRepo;

	@Autowired
	AssociazioneService assServ;

	@Deprecated
	public List<Libro> elencoLibri1() {
		List<Libro> ls = (List<Libro>) libRepo.findAll();
		return ls;
	}

	public LibroDTO2 elencoLibri() {
		LibroDTO2 dto = new LibroDTO2();
		List<Libro> lista = (List<Libro>) libRepo.findAll();
		if (lista.size() > 0) {
			dto.setElencoLibri(lista);
			dto.setLibriTrovati(lista.size());
			return dto;
		}
		return null;
	}

	public Libro elencoLibriByid(Long id) {
		Libro l = libRepo.findById(id).get();
		return l;
	}

	public void inserisciLibroPiuAutori(LibroDTOargs libro, String...autori) throws ElementAlreadyPresentException {
		Libro lib = new Libro();
		if (!libRepo.existsByTitolo(libro.getTitolo())) {
			lib.setTitolo(libro.getTitolo());
			lib.setPrezzo(libro.getPrezzo());
			lib.setAnnoDiProduzione(libro.getAnnoDiProduzione());
			BeanUtils.copyProperties(libro, lib);
			libRepo.save(lib);
			log.info("Libro " + libro.getTitolo() + " salvato");
			Autore aut;
			for (int i = 0; i < autori.length - 1; i++) {
				if (autoRepo.existsBycognome(autori[i])) {
					aut = autoRepo.findByCognome(autori[i]);
					lib.getAutori().add(aut);
					aut.getLibri().add(lib);
				} else {
					throw new NotFoundException("L'autore " + autori[i] + " non è presente");
				}
			}
			Categoria cat = catRepo.findByTipo(libro.getCategoria());
			if (catRepo.existsByTipo(libro.getCategoria())) {
				lib.getCategorie().add(cat);
			} else {
				throw new NotFoundException("La categoria " + libro.getCategoria() + " non è presente");
			}
			cat.getLibri().add(lib);
			libRepo.save(lib);
			// autoRepo.save(aut);
			// catRepo.save(cat);
		} else {
			throw new ElementAlreadyPresentException("Libro " + libro.getTitolo() + " già presente nel sistema");
		}
	}

	public void inserisciLibro(LibroDTO libro) throws ElementAlreadyPresentException {
		Libro lib = new Libro();
		if (!libRepo.existsByTitolo(libro.getTitolo())) {
			lib.setTitolo(libro.getTitolo());
			lib.setPrezzo(libro.getPrezzo());
			lib.setAnnoDiProduzione(libro.getAnnoDiProduzione());
			BeanUtils.copyProperties(libro, lib);
			libRepo.save(lib);
			log.info("Libro " + libro.getTitolo() + " salvato");
			Autore aut = autoRepo.findByCognome(libro.getCognomeAutore1());
			if (autoRepo.existsBycognome(libro.getCognomeAutore1())) {
				lib.getAutori().add(aut);
			} else {
				throw new NotFoundException("L'autore " + libro.getCognomeAutore1() + " non è presente");
			}
			Autore aut2 = autoRepo.findByCognome(libro.getCognomeAutore2());
			if (autoRepo.existsBycognome(libro.getCognomeAutore2())) {
				lib.getAutori().add(aut2);
			} else if (libro.getCognomeAutore2().equalsIgnoreCase("string")) {
				libro.setCognomeAutore2("//");
			} else {
				throw new NotFoundException("L'autore " + libro.getCognomeAutore1() + " non è presente");
			}
			Categoria cat = catRepo.findByTipo(libro.getCategoria());
			if (catRepo.existsByTipo(libro.getCategoria())) {
				lib.getCategorie().add(cat);
			} else {
				throw new NotFoundException("La categoria " + libro.getCategoria() + " non è presente");
			}
			aut.getLibri().add(lib);
			cat.getLibri().add(lib);
			log.info(lib.toString() + " " + aut.toString());
			libRepo.save(lib);
			// autoRepo.save(aut);
			// catRepo.save(cat);
		} else {
			throw new ElementAlreadyPresentException("Libro " + libro.getTitolo() + " già presente nel sistema");
		}
	}

	public void modificaLibro(LibroDTO libro) {
		if (libRepo.existsByTitolo(libro.getTitolo())) {
			Libro lib = libRepo.findByTitolo(libro.getTitolo());
			BeanUtils.copyProperties(libro, lib);
			Autore aut = autoRepo.findByCognome(libro.getCognomeAutore1());
			if (autoRepo.existsBycognome(libro.getCognomeAutore1())) {
				lib.getAutori().add(aut);
			} else {
				throw new NotFoundException("L'autore " + libro.getCognomeAutore1() + " non è presente");
			}
			Autore aut2 = autoRepo.findByCognome(libro.getCognomeAutore2());
			if (autoRepo.existsBycognome(libro.getCognomeAutore2())) {
				lib.getAutori().add(aut2);
			} else if (libro.getCognomeAutore2().equalsIgnoreCase("string")) {
				libro.setCognomeAutore2("//");
			} else {
				throw new NotFoundException("L'autore " + libro.getCognomeAutore1() + " non è presente");
			}
			Categoria cat = catRepo.findByTipo(libro.getCategoria());
			if (catRepo.existsByTipo(libro.getCategoria())) {
				lib.getCategorie().add(cat);
			} else {
				throw new NotFoundException("La categoria " + libro.getCategoria() + " non è presente");
			}
			libRepo.save(lib);
		} else {
			log.info("libro non modificato");
			throw new NotFoundException("Libro " + libro.getTitolo() + " non trovato");
		}
	}

	public void eliminaLibro(String titolo) {
		if (libRepo.existsByTitolo(titolo)) {
			libRepo.deleteByTitolo(titolo);
			log.info("libro " + titolo + ", eliminato");
		} else {
			throw new NotFoundException("libro " + titolo + ", non trovato");
		}
	}

}
