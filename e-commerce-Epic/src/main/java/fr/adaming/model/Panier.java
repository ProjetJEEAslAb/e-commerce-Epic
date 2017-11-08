package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


public class Panier implements Serializable {
 
	
	//association aux lignes de commandes

	private List<LigneCommande> listeLigneCommande;

//=======================================================================//	
	
	//getters et setters
	
	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}
	
}
