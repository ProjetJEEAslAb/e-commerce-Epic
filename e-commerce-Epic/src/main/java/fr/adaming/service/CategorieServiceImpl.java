package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Service("catService")
@Transactional
public class CategorieServiceImpl implements ICategorieService {

	// ==================== 1 =====================
	// Injection des collaborateurs
	// ============================================
	@Autowired
	private ICategorieDao catDao;

	// ==================== 2 =====================
	// Setters pour l'injection
	// ============================================

	public void setaDao(ICategorieDao catDao) {
		this.catDao = catDao;
	}

	// ==================== 3 =====================
	// Méthodes
	// ============================================

	@Override
	public Categorie getCategorieById(Categorie cat) throws Exception {
		// TODO getCategorieById
		return catDao.getCategorieById(cat);
	}

	@Override
	public Categorie addCategorie(Categorie cat) {
		// TODO addCategorie
		return catDao.addCategorie(cat);
	}

	@Override
	public Categorie deleteCategorie(Categorie cat) {
		// TODO deleteCategorie
		return catDao.deleteCategorie(cat);
	}

	@Override
	public Categorie updateCategorie(Categorie cat) {
		// TODO updateCategorie
		return catDao.updateCategorie(cat);
	}

	@Override
	public List<Categorie> getAllCategorie() {
		// TODO getAllCategorie
		return catDao.getAllCategorie();
	}

}
