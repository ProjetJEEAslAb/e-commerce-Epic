package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Client;

@Repository
public class ClientDaoImpl implements IClientDao {

	@Autowired
	private SessionFactory sf;

	// =========================les
	// methodes======================================================//

	@Override
	public Client isExist(Client cl) {

		// recuperation de la session
		Session s = sf.getCurrentSession(); // recupere la session si elle
											// existe sinon doit en creer une
											// --> plus optimiser

		// La requete HQL
		String req = "FROM Client cl WHERE cl.mail=:pMail AND cl.mdp=:pMdp"; // on
																				// utilise
																				// les
																				// param
																				// de
																				// la
																				// classe
																				// car
																				// on
																				// utilise
																				// les
																				// ORM

		// creation d'un objet query
		Query query = s.createQuery(req); // recuperation du query de la session

		// passage des parametres
		query.setParameter("pMail", cl.getMail());
		query.setParameter("pMdp", cl.getMdp());

		// envoyer la requete et recuperation du resultat
		Client clOut = (Client) query.uniqueResult(); // caster car retourne un
														// objet

		return clOut;
	}

	// =============================methode
	// ajouter=========================================//

	@Override
	public Client addClient(Client cl) {
		// recuperation de la session
		Session s = sf.getCurrentSession(); // recupere la session si elle
											// existe sinon doit en creer une
											// --> plus optimiser
		s.save(cl);
		return cl;
	}

	// =============================methode
	// modifier=========================================//

	@Override
	public Client updateClient(Client cl) {

		// recuperation de la session
		Session s = sf.getCurrentSession();

		Client clOut = (Client) s.get(Client.class, cl.getId());

		clOut.setNom(cl.getNom());
		clOut.setId(cl.getId());
		clOut.setMail(cl.getMail());
		clOut.setMdp(cl.getMdp());
		clOut.setTel(cl.getTel());

		s.saveOrUpdate(clOut);

		return clOut;
	}
	// ========================la methode
	// supprimer==========================================//

	@Override
	public int deleteClient(Client cl) {
		
		// recuperation de la session
		Session s = sf.getCurrentSession(); // recupere la session si elle
											// existe sinon doit en creer une
											// --> plus optimiser

		// creation de la requete HQL
		String req = "DELETE Client cl WHERE cl.id=:pIdcl";

		// creation d'un objet query
		Query query = s.createQuery(req);

		// passage des paramètres
		query.setParameter("pIdcl", cl.getId());

		// execution du query
		int verif = query.executeUpdate();

		return verif;
	}

}
