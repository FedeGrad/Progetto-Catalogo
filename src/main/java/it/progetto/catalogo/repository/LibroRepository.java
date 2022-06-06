package it.progetto.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.progetto.catalogo.model.Libro;

public interface LibroRepository extends CrudRepository<Libro, Long> {
	
	@Query(value = "SELECT * FROM libro INNER JOIN libri_autori ON libri_autori.id_libro = libro.id WHERE libri_autori.id_autore = ?1", nativeQuery = true)
//	@Query("SELECT * FROM libro INNER JOIN libri_autori ON libri_autori.id_libro = libro.id WHERE libri_autori.id_autore = ?1")
	public List<Libro> findByLibriAutore (Long id);
	
	@Query(value = "SELECT * FROM libro INNER JOIN libri_categorie ON libri_categorie.id_libro = libro.id WHERE libri_categorie.id_categoria = ?1", nativeQuery = true)
	public List<Libro> findByLibriCategoria(Long id);
	
	public boolean existsByTitolo(String titolo);
	public Libro findByTitolo(String titolo);
	public Long deleteByTitolo(String titolo);

}
