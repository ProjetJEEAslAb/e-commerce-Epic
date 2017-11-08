package fr.adaming.managedBean;

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
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Panier;
import fr.adaming.model.Produit;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "lcMB")
@ViewScoped
public class LigneCommandeManagedBean {

	// =======================================================================//
	// injection dependance
	@EJB
	private ILigneCommandeService ligneCommandeService;
	@EJB
	private IProduitService prodService;
	// =======================================================================//
	// attributs

	private Panier attPanier;
	private List<LigneCommande> listeLigneCommande;
	private Long id_produit;
	private LigneCommande ligneCommande;
	private Commande commande;
	private Client client;

	// Pour l'affichage des tables
	private boolean indice = false;
	// =======================================================================//
	// constructeur vide

	public LigneCommandeManagedBean() {
		this.ligneCommande = new LigneCommande();
	}
	// =======================================================================//

	@PostConstruct
	public void init() {
		// Récupération de la session
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession clientSession = (HttpSession) context.getExternalContext().getSession(false);

		// Récupération du client à partir de la session
		this.client = (Client) clientSession.getAttribute("clientSession");
		this.commande = new Commande();
		this.listeLigneCommande = ligneCommandeService.GetAllLigneCommande(this.client);
	}

	// =======================================================================//
	// getters et setters

	public ILigneCommandeService getLigneCommandeService() {
		return ligneCommandeService;
	}

	public void setLigneCommandeService(ILigneCommandeService ligneCommandeService) {
		this.ligneCommandeService = ligneCommandeService;
	}

	public Panier getAttPanier() {
		return attPanier;
	}

	public void setAttPanier(Panier attPanier) {
		this.attPanier = attPanier;
	}

	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}

	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}

	public boolean isIndice() {
		return indice;
	}

	public void setIndice(boolean indice) {
		this.indice = indice;
	}

	public Long getId_produit() {
		return id_produit;
	}

	public void setId_produit(Long id_produit) {
		this.id_produit = id_produit;
	}

	// =======================================================================//
	// methodes

	public String rechercherLigneCommande() {

		try {
			// trouver le la ligne de commande que l'on cherche
			this.ligneCommande = ligneCommandeService.getLigneCommande(this.ligneCommande);
			this.indice = true;
			return "rechercherLigneCommande";

		} catch (Exception e) {
			this.indice = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("la recherche de la ligne de commande a échoué"));
			return "rechercherLigneCommande";
		}

	}

	public String ajouterLigneCommande() {

		try {
			// Ajouter les informations dans this.panier
			Produit prodAjout = new Produit();
			prodAjout.setIdProduit(this.id_produit);
			this.ligneCommande.setAttProduit(prodAjout);
			prodAjout = prodService.getProduitById(prodAjout);
			this.ligneCommande.setPrix(prodAjout.getPrix() * this.ligneCommande.getQuantite());
			this.ligneCommande = ligneCommandeService.addLigneCommandePanier(this.ligneCommande);
	

			return "panier";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout a échoué"));
			return "ajouterLigneCommande";

		}

	}

	public String supprimerLigneCommande() {
		try {
			// Trouver le produit à supprimer
			LigneCommande lcsup = ligneCommandeService.getLigneCommande(this.ligneCommande);

			// Supprimer le produit recherché
			ligneCommandeService.deleteLigneCommandePanier(lcsup);

			// Actualiser la liste à afficher
			this.listeLigneCommande = ligneCommandeService.GetAllLigneCommande(this.client);

			return "panier";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La suppression a échoué"));
			return "supProduit";

		}

	}

	// TODO updateLigneCommande
	public String updateLigneCommande() {

		// Récupérer le cilent de la session
		this.client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("clientSession");

		try {
			// Trouver la ligne à modifier
			LigneCommande lcUp = ligneCommandeService.getLigneCommande(this.ligneCommande);

			// Modifier la ligne retrouvée
			lcUp.setValide(this.ligneCommande.getValide());

			ligneCommandeService.updateLigneCommande(lcUp);

			// Actualiser la liste à afficher
			List<LigneCommande> liste = ligneCommandeService.GetAllLigneCommande(this.client);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLigneCommande", liste);

			return "panier";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La modification a échoué"));
			return "panier";

		}

	}

	public String Valider() {

		this.listeLigneCommande = ligneCommandeService.GetAllLigneCommande(this.client);

		double sommePrixTotal = 0;
		for (LigneCommande ligne : this.listeLigneCommande) {
			if (ligne.getValide().equals("En attente")) {

				// Récupérer le produit commandé
				Produit prodValide = ligne.getAttProduit();
				prodValide = prodService.getProduitById(prodValide);
				prodValide.setQuantite(prodValide.getQuantite() - ligne.getQuantite());

				if (prodValide.getQuantite() > 0) {

					sommePrixTotal = sommePrixTotal + ligne.getPrix();
					ligne.setValide("Payée");
					this.commande.setClient(this.client);

					prodService.updateProduitByAgent(prodValide);
					ligneCommandeService.updateLigneCommande(ligne);

				} else if (prodValide.getQuantite() == 0) {

					sommePrixTotal = sommePrixTotal + ligne.getPrix();
					ligne.setValide("Payée");
					prodService.deleteProduit(prodValide);

					this.commande.setClient(this.client);

					prodService.updateProduitByAgent(prodValide);
					ligneCommandeService.updateLigneCommande(ligne);

				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("stock epuise"));
					return "panier";
				}
			} else {
				continue;
			}

		}

		return "panier";

	}

	public String Annuler() {

		for (LigneCommande ligne : this.listeLigneCommande) {

			if (ligne.getValide().equals("En attente")) {
				ligneCommandeService.deleteLigneCommandePanier(ligne);
			} else {
				continue;
			}
		}
		return "panier";
	}

}