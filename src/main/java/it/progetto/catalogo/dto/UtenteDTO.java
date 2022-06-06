package it.progetto.catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDTO {
	
	private String username;
	private String password;
	private String email;
	private String roles;

}
