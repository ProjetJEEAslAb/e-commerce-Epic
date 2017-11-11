package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.dao.LigneCommandeDaoImpl;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

@Service("lcService") //pour la declaration du bean 
@Transactional//pour les transactions
public class LigneCommandeServiceImpl implements ILigneCommandeService{

	@Autowired
	private ILigneCommandeDao LigneCommandeDao;
	
	//setter pour l'injection dependance
	public void setLigneCommandeDao(ILigneCommandeDao ligneCommandeDao) {
		LigneCommandeDao = ligneCommandeDao;
	}

//==========================Les methodes==================================================//
	
	@Override
	public List<LigneCommande> GetAllLigneCommande(Client c) {
		return LigneCommandeDao.GetAllLigneCommande(c);
	}

	@Override
	public List<LigneCommande> getLigneCommande(Client c) {
		return LigneCommandeDao.getLigneCommande(c);
	}

	@Override
	public LigneCommande addLigneCommandePanier(LigneCommande lc, Commande co) {
		
		//relier la ligne de commande a une commande
		lc.setAttCommande(co);
		return LigneCommandeDao.addLigneCommandePanier(lc);
	}

	@Override
	public int deleteLigneCommandePanier(LigneCommande lc, Client c) {
		return LigneCommandeDao.deleteLigneCommandePanier(lc,c);
	}

	@Override
	public int updateLigneCommande(LigneCommande lc, Client c) {
		return LigneCommandeDao.updateLigneCommande(lc,c);
	}

}
