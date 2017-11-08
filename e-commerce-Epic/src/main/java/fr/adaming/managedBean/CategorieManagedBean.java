package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.Categorie;
import fr.adaming.service.ICategorieService;

@ManagedBean(name = "catMB")
@RequestScoped
public class CategorieManagedBean implements Serializable {

	// ============ 1. Injection de d�pendance Service ============
	@EJB
	private ICategorieService categorieService;

	// ============ 2. Attributs ============
	private Agent agent;
	private Categorie categorie;

	private List<Categorie> listeCategorie;

	private HttpSession agentSession;

	// Pour l'affichage des tables
	private boolean indice = false;

	// S�lection
	private Categorie selectedCategorie;

	// ============ 3. Constructeur vide ============
	public CategorieManagedBean() {
		this.agent = new Agent();
		this.categorie = new Categorie();
		this.selectedCategorie = new Categorie();
	}

	// Ex�cuter la m�thode juste apr�s l'instanciation du MB
	@PostConstruct
	public void init() {

		// R�cup�ration du contexte
		FacesContext context = FacesContext.getCurrentInstance();

		// R�cup�ration de la session
		this.agentSession = (HttpSession) context.getExternalContext().getSession(false);
		// R�cup�ration de l'agent � partir de la session
		this.agent = (Agent) agentSession.getAttribute("agentSession");
		this.listeCategorie = categorieService.getAllCategorie(this.agent);

	}

	// ============ 4. Getters et Setters ============
	public ICategorieService getCategorieService() {
		return categorieService;
	}

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public HttpSession getAgentSession() {
		return agentSession;
	}

	public void setAgentSession(HttpSession agentSession) {
		this.agentSession = agentSession;
	}

	public boolean isIndice() {
		return indice;
	}

	public void setIndice(boolean indice) {
		this.indice = indice;
	}

	public List<Categorie> getListeCategorie() {
		return listeCategorie;
	}

	public void setListeCategorie(List<Categorie> listeCategorie) {
		this.listeCategorie = listeCategorie;
	}

	public Categorie getSelectedCategorie() {
		return selectedCategorie;
	}

	public void setSelectedCategorie(Categorie selectedCategorie) {
		this.selectedCategorie = selectedCategorie;
	}

	// ============ 5. M�thodes ============

	// TODO getCategorieById
	public String getCategorieById() {

		// R�cup�rer l'agent de la session
		this.agent = (Agent) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("agentSession");

		try {

			this.categorie = categorieService.getCategorieById(this.categorie, this.agent);

			this.indice = true;

			return "findAgent";

		} catch (Exception e) {

			this.indice = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La cat�gorie n'existe pas"));
			return "findAgent";

		}

	}

	

	// TODO addCategorie
	public String addCategorie() {

		// R�cup�rer l'agent de la session
		this.agent = (Agent) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("agentSession");

		try {
			// Ajouter les informations dans this.categorie
			this.categorie.setAttAgent(this.agent);
			this.categorie = categorieService.addCategorie(this.categorie);

			// Actualiser la liste � afficher
			List<Categorie> liste = categorieService.getAllCategorie(this.agent);
			agentSession.setAttribute("categorieListe", liste);

			return "accueilAgent";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout a �chou�"));
			return "addAgent";

		}

	}

	

	// TODO deleteCategorie
	public String deleteCategorie() {

		// R�cup�rer l'agent de la session
		this.agent = (Agent) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("agentSession");

		try {
			// Trouver la cat�gorie � supprimer
			Categorie catDel = categorieService.getCategorieById(this.categorie, this.agent);

			// Supprimer la cat�gorie retrouv�e
			categorieService.deleteCategorie(catDel, this.agent);

			// Actualiser la liste � afficher
			List<Categorie> liste = categorieService.getAllCategorie(this.agent);
			agentSession.setAttribute("categorieListe", liste);

			return "accueilAgent";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La suppression a �chou�"));
			return "deleteAgent";

		}

	}

	

	// TODO updateCategorie
	public String updateCategorie() {

		// R�cup�rer l'agent de la session
		this.agent = (Agent) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("agentSession");

		try {
			// Trouver la cat�gorie � modifier
			Categorie catUp = categorieService.getCategorieById(this.categorie, this.agent);

			// Modifier la cat�gorie retrouv�e
			catUp.setNomCategorie(this.categorie.getNomCategorie());
			catUp.setDescription(this.categorie.getDescription());

			categorieService.updateCategorie(catUp, this.agent);

			// Actualiser la liste � afficher
			List<Categorie> liste = categorieService.getAllCategorie(this.agent);
			agentSession.setAttribute("categorieListe", liste);

			return "accueilAgent";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La modification a �chou�"));
			return "updateAgent";

		}

	}

	

	// TODO Autocomplete Categorie
	public List<Categorie> completeCategorie(String query) {

		List<Categorie> listeFiltree = new ArrayList<Categorie>();

		for (int i = 0; i < this.listeCategorie.size(); i++) {
			Categorie skin = this.listeCategorie.get(i);
			if (skin.getNomCategorie().toLowerCase().startsWith(query.toLowerCase())) {
				listeFiltree.add(skin);
			}
		}

		return listeFiltree;
	}

}
