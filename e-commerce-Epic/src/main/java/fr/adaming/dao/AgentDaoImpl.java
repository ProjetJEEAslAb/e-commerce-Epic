package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Agent;

@Repository
public class AgentDaoImpl implements IAgentDao {

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

	// ===== Vérifier que l'Agent existe dans la BDD
	@Override
	public Agent isExist(Agent a) {
		// TODO isExist

		// 1. Récupération de la session
		Session hbs = sf.getCurrentSession();

		// 2. Requête HQL
		String req = "FROM Agent a WHERE a.mail=:pMail AND a.mdp=:pMdp";

		// 3. Créer un objet Query
		Query hbQuery = hbs.createQuery(req);

		// 4. Donner les paramètres
		hbQuery.setParameter("pMail", a.getMail());
		hbQuery.setParameter("pMdp", a.getMdp());

		// 5. Envoyer la requête et récupérer le résultat
		Agent aOut = (Agent) hbQuery.uniqueResult();

		// 6. Retourner le résultat récupéré
		return aOut;
	}

}
