package fr.adaming.dao;

import fr.adaming.model.Agent;

public interface IAgentDao {
	
	// V�rifier que l'Agent existe dans la BDD
	public Agent isExist(Agent a);

}
