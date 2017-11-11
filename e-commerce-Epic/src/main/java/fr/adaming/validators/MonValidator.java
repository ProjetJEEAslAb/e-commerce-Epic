package fr.adaming.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validatorMail")
//mon validator doit implemneter l'interface validator
public class MonValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String saisie = (String) value;
		if (!saisie.contains("@")) {
			throw new ValidatorException(new FacesMessage("le mail doit contenir un @"));

		}

	}

}
