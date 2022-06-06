package it.progetto.catalogo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import it.progetto.catalogo.dto.LibroDTO2;
import it.progetto.catalogo.dto.UtenteDTO;
import it.progetto.catalogo.dto.UtenteDTO2;
import it.progetto.catalogo.exception.ElementAlreadyPresentException;
import it.progetto.catalogo.impl.Role;
import it.progetto.catalogo.impl.RoleRepository;
import it.progetto.catalogo.impl.User;
import it.progetto.catalogo.impl.UserRepository;
import it.progetto.catalogo.model.Libro;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UtenteService {

	@Autowired
	UserRepository utRepo;

	@Autowired
	RoleRepository roleRepo;

	public UtenteDTO2 elencoUtenti() {
		UtenteDTO2 dto = new UtenteDTO2();
		List<User> lista = utRepo.findAll();
		if (lista.size() > 0) {
			dto.setElencoUtenti(lista);
			dto.setUtentiTrovati(lista.size());
			return dto;
		}
		return null;
	}

	public void inserisciUtente(UtenteDTO user) {
		User user2 = new User();
		BeanUtils.copyProperties(user, user2);
		if (!utRepo.existsByUsername(user.getUsername())) {
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			String elencoRuoli = user.getRoles();
			if (elencoRuoli.isBlank()) {
				elencoRuoli = "ROLE_USER";
			}
			String[] listaRuoli = elencoRuoli.split(",");
			Set<Role> ruoli = new HashSet();
			for (int i = 0; i < listaRuoli.length; i++) {
				Role r = roleRepo.findByRuoloNome(listaRuoli[i]);
				if (r != null) {
					ruoli.add(r);
				} else {
					throw new NotFoundException("Ruolo non trovato");
				}
			}
			user2.setRoles(ruoli);
			utRepo.save(user2);
		}
	}

	public void modificaUtente(UtenteDTO user) throws ElementAlreadyPresentException {
		User user2 = utRepo.findByUsername(user.getUsername()).get();
		if (utRepo.existsByUsername(user.getUsername())) {
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			String elencoRuoli = user.getRoles();
			if (elencoRuoli.isBlank()) {
				elencoRuoli = "ROLE_USER";
			}
			String[] listaRuoli = elencoRuoli.split(",");
			Set<Role> ruoli = new HashSet();
			for (int i = 0; i < listaRuoli.length; i++) {
				Role r = roleRepo.findByRuoloNome(listaRuoli[i]);
				if (r != null) {
					ruoli.add(r);
				} else {
					throw new NotFoundException("Ruolo non trovato");
				}
			}
			user2.setRoles(ruoli);
			utRepo.save(user2);
		} else {
			throw new ElementAlreadyPresentException("Utente non trovato");
		}
	}

	public void eliminaUtente(String username) {
		if (utRepo.existsByUsername(username)) {
			utRepo.deleteByUsername(username);
			log.info("Utente " + username + " eliminato");
		} else {
			throw new NotFoundException("Utente " + username + " non trovato");
		}
	}

}
