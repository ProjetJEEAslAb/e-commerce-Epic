package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import fr.adaming.model.Agent;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.IAgentService;
import fr.adaming.service.ICategorieService;

@ManagedBean(name = "aMB")
@RequestScoped
public class AgentManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ============ 1. Injection de d�pendance Service ============
	@ManagedProperty(value = "#{aService}")
	private IAgentService agentService;
	
	@ManagedProperty(value = "#{catService}")
	private ICategorieService categorieService;

	// ============ 2. Attributs ============
	// Dans la page
	private Agent agent;
	private List<Categorie> listeCategories;
	private List<Produit> listeProduits;

	// ============ 3. Constructeur vide ============
	public AgentManagedBean() {
		this.agent = new Agent();
	}

	// ============ 4. Getters et Setters ============

	public Agent getAgent() {
		return agent;
	}

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	// ============ 5. M�thodes ============
	// TODO seConnecter
	public String seConnecter() {

		try {

			Agent agentOut = agentService.isExist(this.agent);

			// Ajouter la liste des cat�gories avec le agentOut cr�� pr�c�dement
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categorieListe",
					agentOut.getListeCategorie());

			// Ajouter la liste des produits avec le agentOut cr�� pr�c�dement
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitListe",
					agentOut.getListeProduit());

			// Ajouter l'agent dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("agentSession", agentOut);

			return "accueilAgent";

		} catch (Exception e) {

			// Envoyer un message d'erreur
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Identifiant et/ou mot de passe incorrect"));

			return "accueilGeneral";
		}

	}

	// TODO seDeconnecter
	public String seDeconnecter() {

		// R�cup�rer la session
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "admin";
	}

}
