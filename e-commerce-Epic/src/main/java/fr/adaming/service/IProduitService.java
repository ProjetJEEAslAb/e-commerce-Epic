package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Produit;

public interface IProduitService {

	// TODO getAllProduit
	public List<Produit> GetAllProduits();

	// TODO getProduitById
	public Produit getProduitById(Produit pro);

	// TODO deleteProduit
	public int deleteProduit(Produit pro);

	// TODO addProduit
	public Produit addProduitByLc(Produit pro);

	// TODO updateProduit
	public Produit updateProduitByAgent(Produit pro);

}
