package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;
import fr.adaming.model.Produit;
import fr.adaming.service.IClientService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "cMB")
@RequestScoped
public class ClientManagedBean implements Serializable {

	// injection des dependances service

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{clService}")
	private IClientService clientService;

	@ManagedProperty(value = "#{proService}")
	private IProduitService produitService;

	// =======================================================================//

	// les attributs utilis�s dans la page

	private Client client;
	HttpSession session;
	private List<Produit> listeProduits;

	// =======================================================================//

	// Constructeur vide
	public ClientManagedBean() {

	}

	// =======================================================================//
//	// creation de la session client
	@PostConstruct
	public void sessionClient() {

		// recuperation du context
		FacesContext context = FacesContext.getCurrentInstance();

		// recuperer la session a partir du context
		this.session = (HttpSession) context.getExternalContext().getSession(false);

		// recuperation du client a partir de la session
		this.client = new Client();
		//this.client = (Client) session.getAttribute("clientSession");

	}

	// =======================================================================//
	// getters et setters

	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	public IClientService getClientService() {
		return clientService;
	}

	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	public IProduitService getProduitService() {
		return produitService;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	// =======================================================================//
	// les methodes metiers

	public String seConnecterClient() {

		try {
			Client client_out = clientService.isExist(this.client);

			// ajouter le client dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientSession", client_out);

			return "accueilClient";

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("l'identifiant et/ou le mot de passe est erron�"));
		}
		return "seConnecter";
	}

	public String seConnecterClientPanier() {

		try {
			Client client_out = clientService.isExist(this.client);

			// ajouter le client dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientSession", client_out);

			return "ajouterLigneCommande";

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("l'identifiant et/ou le mot de passe est erron�"));
		}
		return "seConnecter";
	}

	// la methode pour se deconnecter

	public String seDeconnecterClient() {

		// recuperer la session et la ferm�

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "accueilGeneral";

	}

	// =======================================================================//
	public String ajouterClient() {

		// appelle de la methode pour ajouter un client
		Client cl = clientService.addClient(this.client);

		if (cl.getId() != 0) {

			return "accueilGeneral";

		} else {

		}
		// afficher le message d'erreur sur la page
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("l'ajout a �chou�"));

		return "accueilGeneral";

	}

	// =======================================================================//
	public String modifierClient() {

		// appelle de la methode pour modifier un client
		Client clientOut = clientService.updateClient(this.client);

		if (clientOut != null) {

			return "accueilClient";

		} else {

			// afficher le message d'erreur sur la page
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("la modifiction a �chou�"));

			return "modifClient";
		}

	}

	// =======================================================================//
	public String suprimerClient() {

		// appelle de la methode
		int clientOut = clientService.deleteClient(this.client);

		if (clientOut == 1) {

			return "accueilGeneral";

		} else {

			// afficher le message d'erreur sur la page
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("la suppression a �chou�"));

			return "supClient";
		}

	}

}
