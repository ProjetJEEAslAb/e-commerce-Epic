package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;
import fr.adaming.model.Client;
import fr.adaming.model.LigneCommande;


@Repository
public class LigneCommandeDaoImpl implements ILigneCommandeDao {

	@Autowired // injection automatique du collaborateur sessionFactoryBean
	private SessionFactory sf;

	// setter pour l'injection 
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

//=========================les methodes======================================================//

	@Override
	public List<LigneCommande> GetAllLigneCommande(Client c) {
		// recuperation de la session
		Session s = sf.getCurrentSession(); // recupere la session si elle
											// existe sinon doit en creer une
											// --> plus optimiser

		// La requete HQL
		String req = "FROM LigneCommande lc WHERE lc.attCommande.client.id=:pIdcl";// id du
																	// formateur
																	// de
																	// l'etudiant

		// creation d'un objet query
		Query query = s.createQuery(req); // recuperation du query de la session

		// passage des paramètres
		query.setParameter("pIdcl", c.getId());

		
		// envoyer la requete et recuperation du resultat
		List<LigneCommande> liste = query.list();

		return liste;
	}

// =============================methode rechercher :  produit en attentes dans ligne de commande=========================================//
	
	@Override
	public List<LigneCommande> getLigneCommande(Client c) {
		
		// recuperation de la session
		Session s = sf.getCurrentSession(); // recupere la session si elle
													// existe sinon doit en creer une
													// --> plus optimiser

		// La requete HQL
		String req = "FROM LigneCommande lc WHERE lc.valide=:pValide_lc AND lc.attCommande.client.id=:pIdcl ";// id du
			
		// creation d'un objet query
		Query query = s.createQuery(req);
		
		// passage des paramètres
		query.setParameter("pValide_lc", "En attente");
		query.setParameter("pIdcl", c.getId());

		// 5. Envoyer la requête et récupérer le résultat
		List<LigneCommande> lcGet = query.list();

		// 6. Retourner la liste récupérée
		return lcGet;
	}
	
// =============================methode ajouter=========================================//

	@Override
	public LigneCommande addLigneCommandePanier(LigneCommande lc) {
		
		// recuperation de la session
		Session s = sf.getCurrentSession();
		
		s.save(lc);
		
		return lc;
	}

//=============================methode supprimer=========================================//

	@Override
	public int deleteLigneCommandePanier(LigneCommande lc, Client c) {
		
		// recuperation de la session
		Session s = sf.getCurrentSession();

		// creation de la requete HQL
		String req = "DELETE LigneCommande lc WHERE lc.id_lc=:pIdlc AND lc.attCommande.id_com=:pIdco";

		// creation d'un objet query
		Query query = s.createQuery(req);

		// passage des paramètres
		
		query.setParameter("pIdlc", lc.getId_lc());
		query.setParameter("pIdco", lc.getAttCommande().getId_com());

		// execution du query
		int verif = query.executeUpdate();

		return verif;
	}
	
// =============================methode modifierr=========================================//

	@Override
	public int updateLigneCommande(LigneCommande lc,Client c) {
		
		// recuperation de la session
		Session s = sf.getCurrentSession();

		//requete HQL
		String req=" UPDATE LigneCommande lc SET lc.quantite=:pQuantite,lc.prix=:pPrix,lc.valide=:pValide WHERE lc.id_lc=:pIdlc AND lc.attCommande.id_com=:pIdco";
		//LigneCommande lclOut = (LigneCommande) s.get(LigneCommande.class, lc.getId_lc());

		// creation d'un objet query
		Query query = s.createQuery(req);

		// passage des paramètres
		query.setParameter("pQuantite",lc.getQuantite());
		query.setParameter("pPrix", lc.getPrix());
		query.setParameter("pValide", lc.getValide());
		query.setParameter("pIdlc", lc.getId_lc());
		query.setParameter("pIdco", lc.getAttCommande().getId_com());
		
//		lclOut.setId_lc(lc.getId_lc());
//		lclOut.setPrix(lc.getPrix());
//		lclOut.setQuantite(lc.getQuantite());

		//s.saveOrUpdate(lclOut);

		//return lclOut;
		
		// execution du query
		int verif = query.executeUpdate();

		return verif;
	}
	
	

	

}
