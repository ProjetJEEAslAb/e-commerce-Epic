package fr.adaming.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "commandes")
public class Commande implements Serializable {

	// les attributs

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_commande")
	private Long id_com;

	@Temporal(TemporalType.DATE)
	private Date dateCommande;

	// =======================================================================//

	// les constructeurs

	// constructeur vide

	public Commande() {
		super();
	}

	// constructeur sans id

	public Commande(Date dateCommande) {
		super();
		this.dateCommande = dateCommande;
	}

	// constructeur avec id

	public Commande(Long id_com, Date dateCommande) {
		super();
		this.id_com = id_com;
		this.dateCommande = dateCommande;
	}

	// =======================================================================//

	// transformation uml en java
	@ManyToOne
	@JoinColumn(name = "client_id", referencedColumnName = "id_client")
	private Client client;

	@OneToMany(mappedBy = "attCommande")
	private List<LigneCommande> listeLigneCommande;

	// =======================================================================//

	// getteurs et setters

	public Long getId_com() {
		return id_com;
	}

	public void setId_com(Long id_com) {
		this.id_com = id_com;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}

	@Override
	public String toString() {
		return "Commande [id_com=" + id_com + ", dateCommande=" + dateCommande + "]";
	}

	// =======================================================================//

	// methode to string

}
