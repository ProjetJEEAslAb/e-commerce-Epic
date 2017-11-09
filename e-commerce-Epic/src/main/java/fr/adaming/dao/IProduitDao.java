package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Agent;
import fr.adaming.model.Produit;

public interface IProduitDao {

public List<Produit> GetAllProduits();
	
	public Produit getProduitById(Produit pro);
	public int deleteProduit (Produit pro);
	public Produit addProduitByLc(Produit pro);
	
	// ============= Méthodes pour Agent =============
	// TODO getProduitById
	public Produit getProduitByIdByAgent(Produit pro) throws Exception;

	// TODO addProduit
	public Produit addProduitByAgent(Produit pro);

	// TODO deleteProduit
	public Produit deleteProduitByAgent(Produit pro);

	// TODO updateProduit
	public Produit updateProduitByAgent(Produit pro);

	// TODO getAllProduit
	public List<Produit> getAllProduitByAgent(Agent a);
		
	
}
