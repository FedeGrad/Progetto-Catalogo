package it.progetto.catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.progetto.catalogo.dto.AutoreDTO;
import it.progetto.catalogo.exception.ElementAlreadyPresentException;
import it.progetto.catalogo.repository.AutoreRepository;
import it.progetto.catalogo.service.AutoreService;

@RestController
@RequestMapping("/autore")
public class AutoreController {
	@Autowired
	AutoreService autoServ;

	@Autowired
	AutoreRepository autoRepo;

	@Operation(summary = "inserisce un Autore nel sistema", 
			   description = "con gli attributi: Nome e Cognome")
	@ApiResponse(responseCode = "200", description = "Autore inserito correttamente nel sistema")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@PostMapping("/inserisciAutore")
	public ResponseEntity inserisciAutore(@RequestBody AutoreDTO autoreDTO) throws ElementAlreadyPresentException {
		autoServ.inserisciAutore(autoreDTO);
		return ResponseEntity.ok("Autore inserito");
	}

	@Operation(summary = "Modifica un Autore nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Autore modificato")
	@ApiResponse(responseCode = "400", description = "Autore non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/modificaAutore")
	public ResponseEntity modificaAutore(@RequestBody AutoreDTO autDTO) {
		autoServ.modificaAutore(autDTO);
		return ResponseEntity.ok("Autore modificato");
	}

	@Operation(summary = "Elimina un Autore nel sistema", description = "a partire dal Cognome")
	@ApiResponse(responseCode = "200", description = "Autore eliminato")
	@ApiResponse(responseCode = "400", description = "Autore non trovato")
	@ApiResponse(responseCode = "500", description = "Errore eliminazione")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/eliminaAutore/{cognome}")
	public ResponseEntity eliminaAutore(@PathVariable String cognome) {
		autoServ.eliminaAutoreByCognome(cognome);
		return ResponseEntity.ok("Autore eliminata");
	}

}
