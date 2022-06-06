package it.progetto.catalogo.repository;

import org.springframework.data.repository.CrudRepository;

import it.progetto.catalogo.model.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
	
	public Categoria findByTipo(String tipo);
	public boolean existsByTipo(String tipo);
	public Long deleteByTipo(String tipo);

}
