package fr.adaming.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ligneComandes")
public class LigneCommande {

	// les attributs

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_lic")
	private int id_lc;

	@Column(name = "qn_lc")
	private int quantite;

	@Column(name = "prix_lc")
	private double prix;

	@Column(name = "valide_lc")
	private String valide = "En attente";

	// associations avec un produit
	@ManyToOne
	@JoinColumn(name = "produit_id", referencedColumnName = "id_pro")
	private Produit attProduit;

	// association avec un panier
	@Transient
	private Panier attPanier;

	// association avec une commande
	@ManyToOne
	@JoinColumn(name = "commande_id", referencedColumnName = "id_commande")
	private Commande attCommande;
	// =======================================================================//

	// les constructeurs

	public LigneCommande() {
		super();
	}

	public LigneCommande(int quantite, double prix, Produit attProduit, Panier attPanier) {
		super();
		this.quantite = quantite;
		this.prix = prix;
		this.attProduit = attProduit;
		this.attPanier = attPanier;
	}

	public LigneCommande(int id_lc, int quantite, double prix, Produit attProduit, Panier attPanier) {
		super();
		this.id_lc = id_lc;
		this.quantite = quantite;
		this.prix = prix;
		this.attProduit = attProduit;
		this.attPanier = attPanier;
	}

	// =======================================================================//

	// getteurs et setters

	public int getId_lc() {
		return id_lc;
	}

	public void setId_lc(int id_lc) {
		this.id_lc = id_lc;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Produit getAttProduit() {
		return attProduit;
	}

	public void setAttProduit(Produit attProduit) {
		this.attProduit = attProduit;
	}

	public Panier getAttPanier() {
		return attPanier;
	}

	public void setAttPanier(Panier attPanier) {
		this.attPanier = attPanier;
	}

	public String getValide() {
		return valide;
	}

	public void setValide(String valide) {
		this.valide = valide;
	}

	public Commande getAttCommande() {
		return attCommande;
	}

	public void setAttCommande(Commande attCommande) {
		this.attCommande = attCommande;
	}
	

	// =======================================================================//

}
