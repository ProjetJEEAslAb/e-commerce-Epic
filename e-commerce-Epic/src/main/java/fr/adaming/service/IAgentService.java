package fr.adaming.service;

import fr.adaming.model.Agent;

public interface IAgentService {

	// V�rifier que l'Agent existe dans la BDD
	public Agent isExist(Agent a);

}
