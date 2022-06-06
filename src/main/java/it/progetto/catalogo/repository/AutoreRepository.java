package it.progetto.catalogo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.progetto.catalogo.model.Autore;

public interface AutoreRepository extends CrudRepository<Autore, Long> {
	
	public boolean existsBynome(String nome);
	public boolean existsBycognome(String cognome);
	public List<Autore> findByNome(String nome);
	public Autore findByCognome(String cognome);
	public Autore findByNomeAndCognome(String nome, String cognome);
	public Long deleteByCognome(String cognome);
}
