package it.progetto.catalogo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTO {
	
	private String titolo;
	private LocalDate annoDiProduzione;
	private double prezzo;
	private String CognomeAutore1;
	private String CognomeAutore2 = "//";
	private String categoria;	

}
