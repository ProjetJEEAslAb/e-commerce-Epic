package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.Categorie;
import fr.adaming.model.Client;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "pMB")
@ViewScoped
public class ProduitManagedBean implements Serializable {

	// =======================================================================//
	// injection dependance
	@EJB
	private IProduitService produitService;
	@EJB
	private ICategorieService categorieService;

	// =======================================================================//
	// attributs
	private Produit produit;
	private Categorie categorie;
	private Long idCategorie;
	private List<Produit> listeProduit;
	private List<Produit> listeProduitAgent;
	private Client client;
	private List<LigneCommande> ligneCommande;
	private List<Produit> selectedProduits;
	private HttpSession clientSession;
	private HttpSession agentSession;
	private Agent agent;

	// Pour l'affichage des tables
	private boolean indice = false;
	// =======================================================================//
	// constructeur vide

	public ProduitManagedBean() {
		this.agent = new Agent();
		this.client = new Client();
		this.produit = new Produit();
	}

	// =======================================================================//
	@PostConstruct
	public void init() {

		// R�cup�ration du contexte
		FacesContext context = FacesContext.getCurrentInstance();

		// R�cup�ration de la session
		this.agentSession = (HttpSession) context.getExternalContext().getSession(false);
		// R�cup�ration de l'agent � partir de la session
		this.agent = (Agent) agentSession.getAttribute("agentSession");
		// this.listeProduitAgent =
		// produitService.getAllProduitByAgent(this.agent);

		// // R�cup�ration de la session
		// this.clientSession = (HttpSession)
		// context.getExternalContext().getSession(false);

		// recuperation du client a partir de la session
		// this.client = (Client) clientSession.getAttribute("clientSession");
		this.listeProduit = produitService.GetAllProduits();
	}

	// =======================================================================//
	// getters et setters

	public IProduitService getProduitService() {
		return produitService;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	public ICategorieService getCategorieService() {
		return categorieService;
	}

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Long getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(Long idCategorie) {
		this.idCategorie = idCategorie;
	}

	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}

	public List<Produit> getListeProduitAgent() {
		return listeProduitAgent;
	}

	public void setListeProduitAgent(List<Produit> listeProduitAgent) {
		this.listeProduitAgent = listeProduitAgent;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<LigneCommande> getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(List<LigneCommande> ligneCommande) {
		this.ligneCommande = ligneCommande;
	}

	public List<Produit> getSelectedProduits() {
		return selectedProduits;
	}

	public void setSelectedProduits(List<Produit> selectedProduits) {
		this.selectedProduits = selectedProduits;
	}

	public HttpSession getClientSession() {
		return clientSession;
	}

	public void setClientSession(HttpSession clientSession) {
		this.clientSession = clientSession;
	}

	public HttpSession getAgentSession() {
		return agentSession;
	}

	public void setAgentSession(HttpSession agentSession) {
		this.agentSession = agentSession;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public boolean isIndice() {
		return indice;
	}

	public void setIndice(boolean indice) {
		this.indice = indice;
	}

	// =======================================================================//
	public List<Produit> completeProduit(String query) {

		List<Produit> listeFiltree = new ArrayList<Produit>();

		for (int i = 0; i < this.listeProduit.size(); i++) {
			Produit skin = this.listeProduit.get(i);
			if (skin.getDesignation().toLowerCase().startsWith(query.toLowerCase())) {
				listeFiltree.add(skin);
			}
		}

		return listeFiltree;

	}

	// =======================================================================//

	

	public String getProduitById() {

		try {
			// trouver le produit que l'on cherche
			this.produit = produitService.getProduitById(this.produit);
			this.indice = true;
			return "rechercherProduit";

		} catch (Exception e) {
			this.indice = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("la recherche de produit a �chou�"));
			return "rechercherProduit";
		}

	}

	// =======================================================================//
	// les methodes
	public String deleteProduit() {

		try {
			// Trouver le produit � supprimer
			Produit proDel = produitService.getProduitById(this.produit);

			// Supprimer le produit recherch�
			produitService.deleteProduit(proDel);

			// Actualiser la liste � afficher
			this.listeProduit = produitService.GetAllProduits();

			return "panier";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La suppression a �chou�"));
			return "supProduit";

		}

	}

	public String addProduitByLc() {

		try {
			// Ajouter les informations dans ligne commande
			this.produit.setLigneCommande(this.ligneCommande);
			

//			// Actualiser la liste � afficher
//			List<Produit> liste = produitService.GetAllProduits();
//			agentSession.setAttribute("produitListe", liste);

			return "Panier";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout a �chou�"));
			return "ajoutProduit";

		}

	}

	// ==================== M�thodes Agent ====================

	// TODO getProduitById
	public String getProduitByIdByAgent() {

		// R�cup�rer l'agent de la session
		this.agent = (Agent) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("agentSession");

		try {

			this.produit = produitService.getProduitByIdByAgent(this.produit, this.agent);

			this.indice = true;

			return "findAgent";

		} catch (Exception e) {

			this.indice = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le produit n'existe pas"));
			return "findAgent";

		}

	}

	// TODO addProduit
	public String addProduitByAgent() {

		// R�cup�rer l'agent de la session
		this.agent = (Agent) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("agentSession");

		try {
			// Ajouter les informations dans this.produit
			Categorie catAjout = new Categorie();
			catAjout.setIdCategorie(this.idCategorie);
			this.produit.setAttAgent(this.agent);
			this.produit.setAttCategorie(catAjout);
			this.produit = produitService.addProduitByAgent(this.produit);

			// Actualiser la liste � afficher
			List<Produit> liste = produitService.getAllProduitByAgent(this.agent);
			agentSession.setAttribute("produitListe", liste);

			return "accueilAgent";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout a �chou�"));
			return "addAgent";

		}

	}

	// TODO deleteProduit
	public String deleteProduitByAgent() {

		// R�cup�rer l'agent de la session
		this.agent = (Agent) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("agentSession");

		try {
			// Trouver la cat�gorie � supprimer
			Produit proDel = produitService.getProduitByIdByAgent(this.produit, this.agent);

			// Supprimer la cat�gorie retrouv�e
			produitService.deleteProduitByAgent(proDel, this.agent);

			// Actualiser la liste � afficher
			List<Produit> liste = produitService.getAllProduitByAgent(this.agent);
			agentSession.setAttribute("produitListe", liste);

			return "accueilAgent";

		} catch (Exception e) {

			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage("La suppression a �chou�"));
			return "deleteAgent";

		}

	}

	// TODO updateProduit
	public String updateProduitByAgent() {

		// R�cup�rer l'agent de la session
		this.agent = (Agent) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("agentSession");
		Categorie catUp = new Categorie();
		catUp.setIdCategorie(this.idCategorie);

		try {
			// Trouver le produit � modifier
			Produit proUp = produitService.getProduitByIdByAgent(this.produit, this.agent);

			// Modifier la cat�gorie retrouv�e
			proUp.setDesignation(this.produit.getDesignation());
			proUp.setDescription(this.produit.getDescription());
			proUp.setPrix(this.produit.getPrix());
			proUp.setQuantite(this.produit.getQuantite());
			proUp.setAttCategorie(catUp);

			produitService.updateProduitByAgent(proUp);

			// Actualiser la liste � afficher
			List<Produit> liste = produitService.GetAllProduits();
			agentSession.setAttribute("produitListe", liste);

			return "accueilAgent";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La modification a �chou�"));
			return "updateAgent";

		}

	}

}
