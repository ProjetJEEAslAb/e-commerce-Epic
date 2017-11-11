package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Agent;
import fr.adaming.model.Produit;

@Service("proService") // pour la declaration du bean
@Transactional // pour les transactions
public class ProduitServiceImpl implements IProduitService {

	@Autowired
	private IProduitDao produitDao;

	// setter pour l'injection dependance
	public void setProduitDao(IProduitDao produitDao) {
		this.produitDao = produitDao;
	}

	// ==========================Les
	// methodes==================================================//

	@Override
	public List<Produit> GetAllProduits() {
		return produitDao.GetAllProduits();
	}

	@Override
	public Produit getProduitById(Produit pro) {
		return produitDao.getProduitById(pro);
	}

	@Override
	public int deleteProduit(Produit pro) {
		return produitDao.deleteProduit(pro);
	}

	@Override
	public Produit addProduitByLc(Produit pro) {
		return produitDao.addProduitByLc(pro);
	}

	@Override
	public Produit updateProduitByAgent(Produit pro) {
		// TODO updateProduit
		return produitDao.updateProduit(pro);
	}

	@Override
	public Produit getImageById(Long id) {
		return produitDao.getImageById(id);
	}
}