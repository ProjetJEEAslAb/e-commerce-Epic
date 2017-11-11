package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

public interface ILigneCommandeService {

	public List<LigneCommande> GetAllLigneCommande(Client c);
	public List<LigneCommande> getLigneCommande(Client c);
	public int deleteLigneCommandePanier(LigneCommande lc, Client c);
	public int updateLigneCommande(LigneCommande lc, Client c);
	public LigneCommande addLigneCommandePanier(LigneCommande lc, Commande co);
	

}
