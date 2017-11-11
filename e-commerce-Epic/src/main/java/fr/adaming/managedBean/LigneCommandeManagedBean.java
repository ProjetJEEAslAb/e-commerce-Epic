package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Panier;
import fr.adaming.model.Produit;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "lcMB")
@RequestScoped
public class LigneCommandeManagedBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// =======================================================================//
	// injection dependance
	@ManagedProperty(value = "#{lcService}")
	private ILigneCommandeService ligneCommandeService;
	
	@ManagedProperty(value = "#{proService}")
	private IProduitService prodService;
	
	@ManagedProperty(value = "#{coService}")
	private ICommandeService commandeService;
	// =======================================================================//
	// attributs

	private Panier attPanier;
	private List<LigneCommande> listeLigneCommande;
	private List<LigneCommande> listeLigneCommandeAttente;
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
		this.commande=new Commande();
	}
	// =======================================================================//

	@PostConstruct
	public void init() {
		// R�cup�ration de la session
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession clientSession = (HttpSession) context.getExternalContext().getSession(false);

		// R�cup�ration du client � partir de la session
		this.client = (Client) clientSession.getAttribute("clientSession");
		this.listeLigneCommande = ligneCommandeService.GetAllLigneCommande(this.client);
		this.listeLigneCommandeAttente=ligneCommandeService.getLigneCommande(this.client);
		
	}

	// =======================================================================//
	// getters et setters



	public void setLigneCommandeService(ILigneCommandeService ligneCommandeService) {
		this.ligneCommandeService = ligneCommandeService;
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

	public void setProdService(IProduitService prodService) {
		this.prodService = prodService;
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
	
	public List<LigneCommande> getListeLigneCommandeAttente() {
		return listeLigneCommandeAttente;
	}

	public void setListeLigneCommandeAttente(List<LigneCommande> listeLigneCommandeAttente) {
		this.listeLigneCommandeAttente = listeLigneCommandeAttente;
	}

	public ILigneCommandeService getLigneCommandeService() {
		return ligneCommandeService;
	}

	public IProduitService getProdService() {
		return prodService;
	}

	public ICommandeService getCommandeService() {
		return commandeService;
	}

	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}
	// =======================================================================//
	// methodes

	

	public String rechercherLigneCommande() {

		try {
			// trouver le la ligne de commande que l'on cherche
			this.ligneCommande = (LigneCommande) ligneCommandeService.getLigneCommande(this.client);
			this.indice = true;
			return "rechercherLigneCommande";

		} catch (Exception e) {
			this.indice = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("la recherche de la ligne de commande a �chou�"));
			return "rechercherLigneCommande";
		}

	}

	public String ajouterLigneCommande() {

		try {
			
			// ajouter le client dans la commande
			this.commande.setClient(this.client);
			
			//ajout de commande 
			this.commande=commandeService.addCommande(this.commande, this.client);
			
			// Ajouter les informations dans this.panier
			Produit prodAjout = new Produit();
			prodAjout.setIdProduit(this.id_produit);
			this.ligneCommande.setAttProduit(prodAjout);
			prodAjout = prodService.getProduitById(prodAjout);
			this.ligneCommande.setPrix(prodAjout.getPrix() * this.ligneCommande.getQuantite());
			
			this.ligneCommande.setAttCommande(this.commande);
			this.ligneCommande = ligneCommandeService.addLigneCommandePanier(this.ligneCommande,this.commande);
		
			//actualiser la liste
			this.listeLigneCommandeAttente = ligneCommandeService.getLigneCommande(this.client);

			return "ajouterLigneCommande";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout a �chou�"));
			return "ajouterLigneCommande";

		}

	}

	public String supprimerLigneCommande() {
		try {
			// Trouver le produit � supprimer
			LigneCommande lcsup = (LigneCommande) ligneCommandeService.getLigneCommande(this.client);

			// Supprimer le produit recherch�
			ligneCommandeService.deleteLigneCommandePanier(lcsup, this.client);

			// Actualiser la liste � afficher
			this.listeLigneCommande = ligneCommandeService.GetAllLigneCommande(this.client);

			return "panier";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La suppression a �chou�"));
			return "supProduit";

		}

	}

	// TODO updateLigneCommande
	public String updateLigneCommande() {

		// R�cup�rer le cilent de la session
		this.client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("clientSession");

		try {
			// Trouver la ligne � modifier
			LigneCommande lcUp = (LigneCommande) ligneCommandeService.getLigneCommande(this.client);

			// Modifier la ligne retrouv�e
			lcUp.setValide(this.ligneCommande.getValide());

			ligneCommandeService.updateLigneCommande(lcUp, this.client);

			// Actualiser la liste � afficher
			List<LigneCommande> liste = ligneCommandeService.GetAllLigneCommande(this.client);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLigneCommande", liste);

			return "panier";

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La modification a �chou�"));
			return "panier";

		}

	}

	public String Valider() {

		this.listeLigneCommande = ligneCommandeService.GetAllLigneCommande(this.client);

		double sommePrixTotal = 0;
		for (LigneCommande ligne : this.listeLigneCommande) {
			if (ligne.getValide().equals("En attente")) {

				// R�cup�rer le produit command�
				Produit prodValide = ligne.getAttProduit();
				prodValide = prodService.getProduitById(prodValide);
				prodValide.setQuantite(prodValide.getQuantite() - ligne.getQuantite());

				if (prodValide.getQuantite() > 0) {

					sommePrixTotal = sommePrixTotal + ligne.getPrix();
					ligne.setValide("Pay�e");
					this.commande.setClient(this.client);

					prodService.updateProduitByAgent(prodValide);
					ligneCommandeService.updateLigneCommande(ligne, this.client);

				} else if (prodValide.getQuantite() == 0) {

					sommePrixTotal = sommePrixTotal + ligne.getPrix();
					ligne.setValide("Pay�e");
					prodService.deleteProduit(prodValide);

					this.commande.setClient(this.client);

					prodService.updateProduitByAgent(prodValide);
					ligneCommandeService.updateLigneCommande(ligne, this.client);

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

//	//========================panier en attente===========================//

//	public String ValiderCommandeEnAttente() {
//
//		double sommePrixTotal = 0;
//		for (LigneCommande ligne : this.listeLigneCommande) {
//			if (ligne.getValide().equals("En attente")) {
//
//				// R�cup�rer le produit command�
//				Produit prodValide = ligne.getAttProduit();
//				prodValide = prodService.getProduitById(prodValide);
//				prodValide.setQuantite(prodValide.getQuantite() - ligne.getQuantite());
//				
//				// Actualiser la liste � afficher
//				this.listeLigneCommandeAttente = (List<LigneCommande>) ligneCommandeService.getLigneCommande(this.client);
//				//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLigneCommandeAttente", listeAttente);
//
//			} else {
//				continue;
//			}
//
//		}
//
//		return "ajouterLigneCommande";
//
//	}
	
	
	//========================methode annuler commande====================//
	public String Annuler() {

		for (LigneCommande ligne : this.listeLigneCommande) {

			if (ligne.getValide().equals("En attente")) {
				ligneCommandeService.deleteLigneCommandePanier(ligne, this.client);
				this.listeLigneCommande = ligneCommandeService.GetAllLigneCommande(this.client);
			} else {
				continue;
			}
		}
		return "panier";
	}

}