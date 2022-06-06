package it.progetto.catalogo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String titolo;
	@NotNull
	private LocalDate annoDiProduzione;
	@NotNull
	private double prezzo;
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name = "LIBRI_AUTORI",
	joinColumns = @JoinColumn(name = "id_libro", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "id_autore", referencedColumnName = "id"))
	private List<Autore> autori = new ArrayList();
	@ManyToMany(mappedBy = "", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name = "LIBRI_CATEGORIE",
	joinColumns = @JoinColumn(name = "id_libro", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "id_categoria", referencedColumnName = "id"))
	private List<Categoria> categorie = new ArrayList<Categoria>();
	
}
