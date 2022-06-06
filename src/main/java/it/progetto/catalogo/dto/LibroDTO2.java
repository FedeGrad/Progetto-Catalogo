package it.progetto.catalogo.dto;

import java.util.List;

import it.progetto.catalogo.model.Libro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTO2 {
	
	List<Libro> elencoLibri;
	private int libriTrovati;
	
}
