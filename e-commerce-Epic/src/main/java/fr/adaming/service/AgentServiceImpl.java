package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IAgentDao;
import fr.adaming.model.Agent;

@Service("aService")
@Transactional
public class AgentServiceImpl implements IAgentService {

	// ==================== 1 =====================
	// Injection des collaborateurs
	// ============================================
	@Autowired
	private IAgentDao aDao;

	// ==================== 2 =====================
	// Setters pour l'injection
	// ============================================

	public void setaDao(IAgentDao aDao) {
		this.aDao = aDao;
	}

	// ==================== 3 =====================
	// Méthodes
	// ============================================

	@Override
	public Agent isExist(Agent a) {
		// TODO isExist
		
		return aDao.isExist(a);
	}

}
