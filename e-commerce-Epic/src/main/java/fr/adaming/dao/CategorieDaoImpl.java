package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;

@Repository
public class CategorieDaoImpl implements ICategorieDao {

	// ==================== 1 =====================
	// Injection automatique du sessionFactoryBean
	// ============================================
	@Autowired
	private SessionFactory sf;

	// ==================== 2 =====================
	// Setter pour l'injection
	// ============================================
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	// ==================== 3 =====================
	// M�thodes
	// ============================================

	// Trouver une cat�gorie par son ID
	@Override
	public Categorie getCategorieById(Categorie cat) throws Exception {
		// TODO getCategorieById

		// 1. R�cup�rer la session
		Session hbs = sf.getCurrentSession();

		// 2. Requ�te HQL
		String req = "FROM Categorie cat WHERE cat.id=:pIdCat";

		// 3. Cr�er un objet Query
		Query hbQuery = hbs.createQuery(req);

		// 4. Donner les param�tres
		hbQuery.setParameter("pIdCat", cat.getIdCategorie());

		// 5. Envoyer la requ�te et r�cup�rer le r�sultat
		Categorie catGet = (Categorie) hbQuery.uniqueResult();

		// 6. Retourner la liste r�cup�r�e
		return catGet;
	}

	// Ajouter une cat�gorie
	@Override
	public Categorie addCategorie(Categorie cat) {
		// TODO addCategorie

		// 1. R�cup�rer la session
		Session hbs = sf.getCurrentSession();

		// 2. Sauvegarder la cat�gorie dans la base de donn�es
		hbs.save(cat);
		return cat;
	}

	// Supprimer une cat�gorie par son ID
	@Override
	public Categorie deleteCategorie(Categorie cat) {
		// TODO deleteCategorie

		// 1. R�cup�rer la session
		Session hbs = sf.getCurrentSession();

		// 2. Supprimer la cat�gorie dans la base de donn�es
		hbs.delete(cat);

		return cat;
	}

	// Modifier une cat�gorie en la r�cup�rant par son ID
	@Override
	public Categorie updateCategorie(Categorie cat) {
		// TODO updateCategorie

		// 1. R�cup�rer la session
		Session hbs = sf.getCurrentSession();

		// 2. Modifier l'�tudiant dans la base de donn�es
		hbs.saveOrUpdate(cat);

		return cat;
	}

	// R�cup�rer la liste de toutes les cat�gories propos�es par le site
	@Override
	public List<Categorie> getAllCategorie() {
		// TODO getAllCategorie

		// 1. R�cup�rer la session
		Session hbs = sf.getCurrentSession();

		// 2. Requ�te HQL
		String req = "FROM Categorie cat";

		// 3. Cr�er un objet Query
		Query hbQuery = hbs.createQuery(req);

		// 4. Envoyer la requ�te et r�cup�rer le r�sultat
		List<Categorie> listeCategorie = hbQuery.list();

		// 5. Retourner la liste r�cup�r�e
		return listeCategorie;
	}

}
