package fr.adaming.dao;

import fr.adaming.model.Client;

public interface IClientDao {
	
	public Client isExist(Client cl);
	public Client addClient(Client cl);
	public Client updateClient (Client cl);
	public int deleteClient (Client cl);

}
