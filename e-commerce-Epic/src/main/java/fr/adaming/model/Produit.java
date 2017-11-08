package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

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
@Table(name="produits")
public class Produit implements Serializable {
	
	// ============ 1. Attributs ============
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pro")
	private Long idProduit;
	
	@Column(name="desi_pro")
	private String designation;
	@Column(name="desc_pro")
	private String description;
	
	@Column(name="prix_pro")
	private double prix;
	
	@Column(name="qte_pro")
	private int quantite;
	
	@Column(name="select_pro",columnDefinition = "TINYINT(1)")
	private boolean selectionne;

	// Association avec une seule Categorie
	@ManyToOne
	@JoinColumn(name="cat_id", referencedColumnName="id_cat")
	private Categorie attCategorie;
	
	// Association avec un seul Agent
	@ManyToOne
	@JoinColumn(name="agent_id", referencedColumnName="id_a")
	private Agent attAgent;
	
	//association avec plusieurs ligne de commandes
	@OneToMany(mappedBy="attProduit",fetch=FetchType.EAGER)
	private List<LigneCommande> ligneCommande;
	
	// ============ 2. Constructeurs ============
	// Vide
	public Produit() {
		super();
	}

	// Sans id
	public Produit(String designation, String description, double prix, int quantite, boolean selectionne) {
		super();
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
	}

	// Avec id
	public Produit(Long idProduit, String designation, String description, double prix, int quantite,
			boolean selectionne) {
		super();
		this.idProduit = idProduit;
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
	}

	// ============ 3. Getters et Setters ============
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public Long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}

	public Categorie getAttCategorie() {
		return attCategorie;
	}

	public void setAttCategorie(Categorie attCategorie) {
		this.attCategorie = attCategorie;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
	}

	public Agent getAttAgent() {
		return attAgent;
	}

	public void setAttAgent(Agent attAgent) {
		this.attAgent = attAgent;
	}

	public List<LigneCommande> getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(List<LigneCommande> ligneCommande) {
		this.ligneCommande = ligneCommande;
	}
	
	

}
