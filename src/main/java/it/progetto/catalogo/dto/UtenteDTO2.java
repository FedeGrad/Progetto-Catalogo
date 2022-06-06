package it.progetto.catalogo.dto;

import java.util.List;

import it.progetto.catalogo.impl.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDTO2 {
	
	List<User> elencoUtenti;
	private int utentiTrovati;

}
