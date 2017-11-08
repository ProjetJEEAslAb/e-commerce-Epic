package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Categorie implements Serializable {
	
	// ============ 1. Attributs ============
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cat")
	private Long idCategorie;
	
	@Column(name="nom_cat")
	private String nomCategorie;
	@Column(name="desc_cat")
	private String description;
	
	// Association UML avec plusieurs Produit
	@OneToMany(mappedBy="attCategorie", cascade=CascadeType.REMOVE)
	private List<Produit> listeProduits;
	
	// Association avec un seul Agent
	@ManyToOne
	@JoinColumn(name="agent_id", referencedColumnName="id_a")
	private Agent attAgent;
	
	// ============ 2. Constructeurs ============
	// Vide
	public Categorie() {
		super();
	}

	// Sans id
	public Categorie(String nomCategorie, String description) {
		super();
		this.nomCategorie = nomCategorie;
		this.description = description;
	}

	// Avec id
	public Categorie(Long idCategorie, String nomCategorie, String description) {
		super();
		this.idCategorie = idCategorie;
		this.nomCategorie = nomCategorie;
		this.description = description;
	}

	// ============ 3. Getters et Setters ============
	

	public String getNomCategorie() {
		return nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}
	
	public Long getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(Long idCategorie) {
		this.idCategorie = idCategorie;
	}

	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Agent getAttAgent() {
		return attAgent;
	}

	public void setAttAgent(Agent attAgent) {
		this.attAgent = attAgent;
	}
	
	
	
	

}
