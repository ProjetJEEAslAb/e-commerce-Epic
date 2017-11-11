package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Agent;
import fr.adaming.model.Produit;

@Repository
public class ProduitDaoImpl implements IProduitDao {

	@Autowired // injection automatique du collaborateur sessionFactoryBean
	private SessionFactory sf;

	// setter pour l'injection (pas obligatoire mais on sen servira pour la
	// suite de notre application)
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	// =========================les
	// methodes======================================================//

	@Override
	public List<Produit> GetAllProduits() {

		// recuperation de la session
		Session s = sf.getCurrentSession();

		// La requete HQL
		String req = "FROM Produit pro";

		// creation d'un objet query
		Query query = s.createQuery(req); // recuperation du query de la session

		// envoyer la requete et recuperation du resultat
		List<Produit> liste = query.list();

		return liste;
	}

	// ================================la methode
	// rechercher====================================//

	@Override
	public Produit getProduitById(Produit pro) {
		// recuperation de la session
		Session s = sf.getCurrentSession();

		// creation de la requete HQL
		String req = "FROM Produit pro WHERE pro.idProduit=:pIdpro";

		// creation d'un objet query
		Query query = s.createQuery(req); // recuperation du query de la session

		// passage des paramètres
		query.setParameter("pIdpro", pro.getIdProduit());

		// envoyer la requete et recuperation du resultat
		Produit proOut = (Produit) query.uniqueResult();

		return proOut;
	}

	// ================================la methode
	// supprimer====================================//
	@Override
	public int deleteProduit(Produit pro) {
		// recuperation de la session
		Session s = sf.getCurrentSession(); // recupere la session si elle
											// existe sinon doit en creer une
											// --> plus optimiser

		// creation de la requete HQL
		String req = "DELETE Produit pro WHERE pro.idProduit=:pIdpro";

		// creation d'un objet query
		Query query = s.createQuery(req);

		// passage des paramètres
		query.setParameter("pIdpro", pro.getIdProduit());

		// execution du query
		int verif = query.executeUpdate();

		return verif;
	}

	// ==================================methode
	// ajouter========================================//

	@Override
	public Produit addProduitByLc(Produit pro) {
		// recuperation de la session
		Session s = sf.getCurrentSession(); // recupere la session si elle
											// existe sinon doit en creer une
											// --> plus optimiser
		s.save(pro);
		return pro;
	}

	@Override
	public Produit updateProduit(Produit pro) {
		// TODO updateProduit

		// 1. Récupérer la session
		Session hbs = sf.getCurrentSession();

		// 2. Modifier l'étudiant dans la base de données
		hbs.saveOrUpdate(pro);

		return pro;
	}

}
