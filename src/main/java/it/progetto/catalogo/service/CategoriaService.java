package it.progetto.catalogo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import it.progetto.catalogo.dto.AutoreDTO;
import it.progetto.catalogo.dto.CategoriaDTO;
import it.progetto.catalogo.dto.LibroDTO;
import it.progetto.catalogo.exception.ElementAlreadyPresentException;
import it.progetto.catalogo.model.Autore;
import it.progetto.catalogo.model.Categoria;
import it.progetto.catalogo.model.Libro;
import it.progetto.catalogo.repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoriaService {

	@Autowired
	CategoriaRepository catRepo;

	public void inserisciCategoria(CategoriaDTO categoria) throws ElementAlreadyPresentException {
		Categoria cat = new Categoria();
		if (!catRepo.existsByTipo(categoria.getTipo())) {
			BeanUtils.copyProperties(categoria, cat);
			catRepo.save(cat);
			log.info("categoria " + categoria.getTipo() + " salvata");
		} else {
			throw new ElementAlreadyPresentException("Categoria già presente nel sistema");
		}
	}

	public void eliminaCategoria(String tipo) {
		if (catRepo.existsByTipo(tipo)) {
			catRepo.deleteByTipo(tipo);
			log.info("categoria " + tipo + " eliminata");
		} else {
			throw new NotFoundException("Categoria " + tipo + " non trovata");
		}
	}

	public void modificaCategoria(CategoriaDTO categoria) {
		if (catRepo.existsByTipo(categoria.getTipo())) {
			Categoria cat = catRepo.findByTipo(categoria.getTipo());
			BeanUtils.copyProperties(categoria, cat);
			catRepo.save(cat);
			log.info("categoria " + categoria.getTipo() + " modifiacata");
		} else {
			throw new NotFoundException("Categoria " + categoria.getTipo() + " non trovata");
		}
	}

}
