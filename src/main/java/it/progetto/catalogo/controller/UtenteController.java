package it.progetto.catalogo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.progetto.catalogo.dto.UtenteDTO;
import it.progetto.catalogo.exception.ElementAlreadyPresentException;
import it.progetto.catalogo.impl.UserRepository;
import it.progetto.catalogo.service.UtenteService;

@RestController
@RequestMapping("/user")
public class UtenteController {

	@Autowired
	UserRepository utRepo;
	
	@Autowired
	UtenteService utServ;
	
	@Operation(summary = "Trova Utenti nel sistema", description = "ritorna la lista di tutti gli utenti presenti")
	@ApiResponse(responseCode = "200", description = "Utenti trovati")
	@ApiResponse(responseCode = "400", description = "Utenti non trovati")
	@GetMapping("/getAllUtenti")
	public ResponseEntity getUtenti() {
		return ResponseEntity.ok(utServ.elencoUtenti());
	}
	
	@Operation(summary = "inserisce un utente nel sistema", 
			   description = "con gli attributi: Username, password, email e Ruolo")
	@ApiResponse(responseCode = "200", description = "Utente inserito correttamente nel sistema")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@PostMapping("/inserisciUtente")
	public ResponseEntity inserisciUtente(@Valid @RequestBody UtenteDTO utDTO) throws ElementAlreadyPresentException {
		utServ.inserisciUtente(utDTO);
		return ResponseEntity.ok("Utente inserito");
	}
	
	@Operation(summary = "Modifica un utente nel sistema", description = "Modifica un utente nel sistema")
	@ApiResponse(responseCode = "200", description = "Utente modificato")
	@ApiResponse(responseCode = "400", description = "Utente non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/modificaUtente")
	public ResponseEntity modificaUtente(@RequestBody UtenteDTO dto) throws ElementAlreadyPresentException {
		utServ.modificaUtente(dto);
		return ResponseEntity.ok("categoria inserita");
	}
	
	@Operation(summary = "Elimina un Utente nel sistema", description = "a partire dall'username")
	@ApiResponse(responseCode = "200", description = "Utente eliminato")
	@ApiResponse(responseCode = "400", description = "Utente non trovato")
	@ApiResponse(responseCode = "500", description = "Errore eliminazione")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/eliminaUtente/{username}")
	public ResponseEntity eliminaLibro(@RequestAttribute String username) {
		utServ.eliminaUtente(username);
		return ResponseEntity.ok("Libro eliminato");
	}

}
