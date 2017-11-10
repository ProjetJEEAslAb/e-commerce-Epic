package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		String req = "FROM LigneCommande lc";// id du
																	// formateur
																	// de
																	// l'etudiant

		// creation d'un objet query
		Query query = s.createQuery(req); // recuperation du query de la session

		
		
		// envoyer la requete et recuperation du resultat
		List<LigneCommande> liste = query.list();

		return liste;
	}

// =============================methode rechercher=========================================//
	
	@Override
	public LigneCommande getLigneCommande(LigneCommande lc) {
		return null;
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
	public int deleteLigneCommandePanier(LigneCommande lc) {
		
		// recuperation de la session
		Session s = sf.getCurrentSession();

		// creation de la requete HQL
		String req = "DELETE LigneCommande lc WHERE lc.id_lc=:pIdlc";

		// creation d'un objet query
		Query query = s.createQuery(req);

		// passage des paramètres
		query.setParameter("pIdlc", lc.getId_lc());

		// execution du query
		int verif = query.executeUpdate();

		return verif;
	}
	
// =============================methode modifierr=========================================//

	@Override
	public LigneCommande updateLigneCommande(LigneCommande lc) {
		
		// recuperation de la session
		Session s = sf.getCurrentSession();

		LigneCommande lclOut = (LigneCommande) s.get(LigneCommande.class, lc.getId_lc());

		lclOut.setId_lc(lc.getId_lc());
		lclOut.setPrix(lc.getPrix());
		lclOut.setQuantite(lc.getQuantite());

		s.saveOrUpdate(lclOut);

		return lclOut;
	}

}
