package fr.adaming.managedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;

@ManagedBean(name = "coMB")
@RequestScoped
public class CommandeManagedBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// =======================================================================//
	// injection dependance service

	@ManagedProperty(value = "#{coService}")
	private ICommandeService commandeService;

	@ManagedProperty(value = "#{clService}")
	private IClientService clientService;

	// =======================================================================//
	// attributs

	private LigneCommande ligneCommande;
	private Commande commande;
	private Client client;
	private boolean indice = false;

	HttpSession maSession;

	// =======================================================================//
	// constructeur vide

	public CommandeManagedBean() {
		this.commande = new Commande();
	}

	// =======================================================================//

	@PostConstruct
	public void init() {

		maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.client = (Client) maSession.getAttribute("clSession");

	}

	// =======================================================================//
	// getters et setters
	public ICommandeService getCommandeService() {
		return commandeService;
	}

	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}

	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public IClientService getClientService() {
		return clientService;
	}

	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	public boolean isIndice() {
		return indice;
	}

	public void setIndice(boolean indice) {
		this.indice = indice;
	}

	// =======================================================================//
	// methodes

	@SuppressWarnings("unused")
	public String ajouterCommande() {

		// ajouter le client dans la session
		this.client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clientSession");
		
	
		
		Date dateCommande = new Date();
		
		Commande co=commandeService.addCommande(this.commande, this.client);
		
		co.setDateCommande(dateCommande);
		co.setClient(this.client);
		
		System.out.println("comande managedBean : " + commande);

		if (co != null) {

			System.out.println("==============================="+dateCommande);
			
			return "ajouterLigneCommande";

		} else {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout a échoué"));
			
			return "accueilgeneral";

		}

	}

}
