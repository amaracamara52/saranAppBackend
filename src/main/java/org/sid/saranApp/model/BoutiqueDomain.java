package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class BoutiqueDomain extends AbstractDomainClass {
	
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private DomainBoutique domainBoutique;
	
	public Boutique getBoutique() {
		return boutique;
	}
	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}
	public DomainBoutique getDomainBoutique() {
		return domainBoutique;
	}
	public void setDomainBoutique(DomainBoutique domainBoutique) {
		this.domainBoutique = domainBoutique;
	}
	
	

}
