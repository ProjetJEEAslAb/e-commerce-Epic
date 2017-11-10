package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Client;
import fr.adaming.model.LigneCommande;

public interface ILigneCommandeDao {
	
	public List<LigneCommande> GetAllLigneCommande(Client c);
	public List<LigneCommande> getLigneCommande(Client c);
	public LigneCommande addLigneCommandePanier(LigneCommande lc);
	public int deleteLigneCommandePanier(LigneCommande lc);
	public LigneCommande updateLigneCommande(LigneCommande lc);
	

}
