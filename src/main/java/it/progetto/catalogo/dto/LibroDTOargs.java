package it.progetto.catalogo.dto;

import java.time.LocalDate;
import java.util.List;

import it.progetto.catalogo.model.Libro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTOargs {
	
	private String titolo;
	private LocalDate annoDiProduzione;
	private double prezzo;
	private String categoria;
	
}
