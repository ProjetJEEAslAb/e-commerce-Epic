package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.dao.LigneCommandeDaoImpl;
import fr.adaming.model.Client;
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
		return null;
	}

	@Override
	public LigneCommande getLigneCommande(LigneCommande lc) {
		return LigneCommandeDao.getLigneCommande(lc);
	}

	@Override
	public LigneCommande addLigneCommandePanier(LigneCommande lc) {
		return LigneCommandeDao.addLigneCommandePanier(lc);
	}

	@Override
	public int deleteLigneCommandePanier(LigneCommande lc) {
		return LigneCommandeDao.deleteLigneCommandePanier(lc);
	}

	@Override
	public LigneCommande updateLigneCommande(LigneCommande lc) {
		return LigneCommandeDao.updateLigneCommande(lc);
	}

}
