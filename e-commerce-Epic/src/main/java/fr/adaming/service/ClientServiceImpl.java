package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.model.Client;

@Service("clService") //pour la declaration du bean 
@Transactional//pour les transactions
public class ClientServiceImpl implements IClientService{

	@Autowired
	private IClientDao clientDao;
	
	//setter pour l'injection dependance
	public void setClientDao(IClientDao clientDao) {
		this.clientDao = clientDao;
	}

//==========================Les methodes==================================================//
	@Override
	public Client isExist(Client cl) {
		return clientDao.isExist(cl);
	}

	
	@Override
	public Client addClient(Client cl) {
		return clientDao.addClient(cl);
	}

	@Override
	public Client updateClient(Client cl) {
		return clientDao.updateClient(cl);
	}

	@Override
	public int deleteClient(Client cl) {
		return clientDao.deleteClient(cl);
	}
}
