package fr.adaming.managedBean;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import fr.adaming.model.Produit;
import fr.adaming.service.IProduitService;

@ManagedBean(name="iMB")
@ApplicationScoped
public class ImageBean implements Serializable {

	@ManagedProperty(value = "#{proService}")
	private IProduitService produitService;

	// constructeur vide

	public ImageBean() {
		super();
	}

	// getters et setters
	public IProduitService getProduitService() {
		return produitService;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	// methode

	public StreamedContent getImage() throws IOException {

		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String imageId = context.getExternalContext().getRequestParameterMap().get("idProduit");
			Produit prod = new Produit();
			prod.setIdProduit(Long.valueOf(imageId));
			Produit produitImage = produitService.getProduitById(prod);
			return new DefaultStreamedContent(new ByteArrayInputStream(produitImage.getImageBytes()));
		}

	}

}
