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
	// M�thodes
	// ============================================

	// ===== V�rifier que l'Agent existe dans la BDD
	@Override
	public Agent isExist(Agent a) {
		// TODO isExist

		// 1. R�cup�ration de la session
		Session hbs = sf.getCurrentSession();

		// 2. Requ�te HQL
		String req = "FROM Agent a WHERE a.mail=:pMail AND a.mdp=:pMdp";

		// 3. Cr�er un objet Query
		Query hbQuery = hbs.createQuery(req);

		// 4. Donner les param�tres
		hbQuery.setParameter("pMail", a.getMail());
		hbQuery.setParameter("pMdp", a.getMdp());

		// 5. Envoyer la requ�te et r�cup�rer le r�sultat
		Agent aOut = (Agent) hbQuery.uniqueResult();

		// 6. Retourner le r�sultat r�cup�r�
		return aOut;
	}

}
