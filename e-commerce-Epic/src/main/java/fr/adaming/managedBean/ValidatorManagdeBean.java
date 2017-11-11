package fr.adaming.managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean(name="vMB")
@RequestScoped
public class ValidatorManagdeBean {
	
	//declaration des attributs du managedbean
	private int quantite;

	
	
	//constructeur vide
	
	public ValidatorManagdeBean() {
		
	}

	
	//getters et setters
	public int getQuantite() {
		return quantite;
	}



	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	
	
	//methodes du managedbean
	
		public void validerQuantite(FacesContext context, UIComponent composant, Object value) //deleque une exception
				throws ValidatorException {
			
			int saisie=(int) value;
				if(saisie<=0){
					throw new ValidatorException(new FacesMessage("la quantité doit être non nulle"));
					
				}
				}
	
	

}
