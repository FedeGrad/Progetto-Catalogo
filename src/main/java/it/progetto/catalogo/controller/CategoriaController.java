package it.progetto.catalogo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
import it.progetto.catalogo.dto.CategoriaDTO;
import it.progetto.catalogo.dto.EsempioDateDTO;
import it.progetto.catalogo.exception.ElementAlreadyPresentException;
import it.progetto.catalogo.model.Categoria;
import it.progetto.catalogo.repository.AutoreRepository;
import it.progetto.catalogo.repository.CategoriaRepository;
import it.progetto.catalogo.service.AutoreService;
import it.progetto.catalogo.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	@Autowired
	CategoriaService catServ;

	@Autowired
	CategoriaRepository catRepo;

	@PostMapping("/test")
	public ResponseEntity testDate(@RequestBody EsempioDateDTO dto) {
		System.out.println(dto);
		DateTimeFormatter dtForm = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter dtimeForm = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		System.out.println(dto.getData1().format(dtForm));
		System.out.println(dto.getDataTempo().format(dtimeForm));
		LocalDate dataParse = LocalDate.parse("2021-01-25");
		System.out.println(dataParse);
		dtForm = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		dataParse = LocalDate.parse("20/01/2021", dtForm);
		System.out.println(dataParse);
		dataParse = LocalDate.parse("20/01/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		System.out.println(dataParse);
		return ResponseEntity.ok(dto);
	}
	
	
	@Operation(summary = "inserisce una categoria nel sistema", 
			   description = "con gli attributi: tipo")
	@ApiResponse(responseCode = "200", description = "Categoria inserito correttamente nel sistema")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@PostMapping
	public ResponseEntity inserisciCategoria(@RequestBody CategoriaDTO catDTO) throws ElementAlreadyPresentException {
		catServ.inserisciCategoria(catDTO);
		return ResponseEntity.ok("categoria inserita");
	}
	
	@Operation(summary = "Modifica una categoria nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Categoria modificata")
	@ApiResponse(responseCode = "400", description = "Categortia non trovata")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/modificaCategoria")
	public ResponseEntity modificaCategoria (@RequestBody CategoriaDTO catDTO) {
		catServ.modificaCategoria(catDTO);
		return ResponseEntity.ok("categoria modificata");
	}

	@Operation(summary = "Elimina una categoria nel sistema", description = "a partire dal tipo")
	@ApiResponse(responseCode = "200", description = "Categoria eliminata")
	@ApiResponse(responseCode = "400", description = "Categoria non trovata")
	@ApiResponse(responseCode = "500", description = "Errore eliminazione")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/eliminaCategoria/{tipo}")
	public ResponseEntity eliminaCategoria(@PathVariable String tipo) {
		catServ.eliminaCategoria(tipo);
		return  ResponseEntity.ok("Categoria eliminata");
	}
	
}
