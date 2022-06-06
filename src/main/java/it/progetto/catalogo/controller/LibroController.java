package it.progetto.catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.progetto.catalogo.dto.LibroDTO;
import it.progetto.catalogo.dto.LibroDTOargs;
import it.progetto.catalogo.exception.ElementAlreadyPresentException;
import it.progetto.catalogo.repository.AutoreRepository;
import it.progetto.catalogo.repository.LibroRepository;
import it.progetto.catalogo.service.LibroService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/libro")
@Slf4j
public class LibroController {

	@Autowired
	LibroService libServ;
	@Autowired
	LibroRepository libRepo;
	@Autowired
	AutoreRepository autoRepo;

	@Operation(summary = "inserisce libro nel sistema", 
			description = "con gli attributi: titolo, data produzione, prezzo, autore e categoria")
	@ApiResponse(responseCode = "200", description = "Libro inserito correttamente nel sistema")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@PostMapping("/inserisciLibro")
	public ResponseEntity inserisciLibro(@RequestBody LibroDTO libDTO) throws ElementAlreadyPresentException {
		libServ.inserisciLibro(libDTO);
		return ResponseEntity.ok("Libro inserito");
	}

	@PostMapping("/inserisciLibroPiuAutori")
	public ResponseEntity inserisciLibroPlus(@RequestBody LibroDTOargs libDTO, 
			@RequestBody String a1, 
			@RequestBody String a2) throws ElementAlreadyPresentException {
		libServ.inserisciLibroPiuAutori(libDTO, a1, a2);
		return ResponseEntity.ok("Libro inserito");
	}

	@Operation(summary = "Trova libri nel sistema", description = "riferiti ad un determinato autore")
	@ApiResponse(responseCode = "200", description = "Libri trovati")
	@ApiResponse(responseCode = "400", description = "Libri non trovati")
	@GetMapping("/trovaLibriByAutore/{id_autore}")
	public ResponseEntity getLibriByAutore(@PathVariable("id_autore") Long id) {
		if (autoRepo.existsById(id)) {
			return ResponseEntity.ok(libRepo.findByLibriAutore(id));
		} else {
			throw new NotFoundException("Nessun libro trovato");
		}
	}
	
	@Operation(summary = "Trova libri nel sistema", description = "riferiti ad una determinata categoria")
	@ApiResponse(responseCode = "200", description = "Libri trovati")
	@ApiResponse(responseCode = "400", description = "Libri non trovati")
	@GetMapping("/trovaLibriByCategoria/{id_categoria}")
	public ResponseEntity getLibriByCAtegoria(@PathVariable("id_categoria") Long id) {
		if (autoRepo.existsById(id)) {
			return ResponseEntity.ok(libRepo.findByLibriCategoria(id));
		} else {
			throw new NotFoundException("Nessun libro trovato");
		}
	}

	@Operation(summary = "Trova libri nel sistema", description = "ritorna la lista di tutti i libri presenti")
	@ApiResponse(responseCode = "200", description = "Libri trovati")
	@ApiResponse(responseCode = "400", description = "Libri non trovati")
	@GetMapping("/getAllLibri")
	public ResponseEntity getLibri() {
		return ResponseEntity.ok(libServ.elencoLibri());
	}

	@Operation(summary = "Trova libri nel sistema", description = "ritorna la lista di tutti i libri presenti")
	@ApiResponse(responseCode = "200", description = "Libri trovati")
	@ApiResponse(responseCode = "400", description = "Libri non trovati")
	@GetMapping("/getAllLibri/{id}")
	public ResponseEntity getLibriByID(@PathVariable Long id) {
		return ResponseEntity.ok(libServ.elencoLibriByid(id));
	}

	@Operation(summary = "Modifica un libro nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Libro modificato")
	@ApiResponse(responseCode = "400", description = "Libro non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/modificaLibro")
	public ResponseEntity modificaLibro(@RequestBody LibroDTO libro) {
		libServ.modificaLibro(libro);
		return ResponseEntity.ok("categoria inserita");
	}

	@Operation(summary = "Elimina un libro nel sistema", description = "a partire dal titolo")
	@ApiResponse(responseCode = "200", description = "Libro eliminato")
	@ApiResponse(responseCode = "400", description = "Libro non trovato")
	@ApiResponse(responseCode = "500", description = "Errore eliminazione")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/eliminaLibro/{titolo}")
	public ResponseEntity eliminaLibro(@PathVariable String titolo) {
		libServ.eliminaLibro(titolo);
		return ResponseEntity.ok("Libro eliminato");
	}

}
