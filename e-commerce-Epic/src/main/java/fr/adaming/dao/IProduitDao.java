package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Agent;
import fr.adaming.model.Produit;

public interface IProduitDao {

	// TODO getAllProduit
	public List<Produit> GetAllProduits();

	// TODO getProduitById
	public Produit getProduitById(Produit pro);

	// TODO deleteProduit
	public int deleteProduit(Produit pro);

	// TODO addProduit
	public Produit addProduitByLc(Produit pro);

	// TODO updateProduit
	public Produit updateProduit(Produit pro);
}
