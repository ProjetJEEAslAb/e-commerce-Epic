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
	// Méthodes
	// ============================================

	// Trouver une catégorie par son ID
	@Override
	public Categorie getCategorieById(Categorie cat) throws Exception {
		// TODO getCategorieById

		// 1. Récupérer la session
		Session hbs = sf.getCurrentSession();

		// 2. Requête HQL
		String req = "FROM Categorie cat WHERE cat.id=:pIdCat";

		// 3. Créer un objet Query
		Query hbQuery = hbs.createQuery(req);

		// 4. Donner les paramètres
		hbQuery.setParameter("pIdCat", cat.getIdCategorie());

		// 5. Envoyer la requête et récupérer le résultat
		Categorie catGet = (Categorie) hbQuery.uniqueResult();

		// 6. Retourner la liste récupérée
		return catGet;
	}

	// Ajouter une catégorie
	@Override
	public Categorie addCategorie(Categorie cat) {
		// TODO addCategorie

		// 1. Récupérer la session
		Session hbs = sf.getCurrentSession();

		// 2. Sauvegarder la catégorie dans la base de données
		hbs.save(cat);
		return cat;
	}

	// Supprimer une catégorie par son ID
	@Override
	public Categorie deleteCategorie(Categorie cat) {
		// TODO deleteCategorie

		// 1. Récupérer la session
		Session hbs = sf.getCurrentSession();

		// 2. Supprimer la catégorie dans la base de données
		hbs.delete(cat);

		return cat;
	}

	// Modifier une catégorie en la récupérant par son ID
	@Override
	public Categorie updateCategorie(Categorie cat) {
		// TODO updateCategorie

		// 1. Récupérer la session
		Session hbs = sf.getCurrentSession();

		// 2. Modifier l'étudiant dans la base de données
		hbs.saveOrUpdate(cat);

		return cat;
	}

	// Récupérer la liste de toutes les catégories proposées par le site
	@Override
	public List<Categorie> getAllCategorie() {
		// TODO getAllCategorie

		// 1. Récupérer la session
		Session hbs = sf.getCurrentSession();

		// 2. Requête HQL
		String req = "FROM Categorie cat";

		// 3. Créer un objet Query
		Query hbQuery = hbs.createQuery(req);

		// 4. Envoyer la requête et récupérer le résultat
		List<Categorie> listeCategorie = hbQuery.list();

		// 5. Retourner la liste récupérée
		return listeCategorie;
	}

}
