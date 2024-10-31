package org.sid.saranApp.dto;

public class PageDto {

    int size;
    long totalElements;
    int totalPages;
    int pageNumber;
    int montant;
    int nombrePatient;
    int montantLabo;
    int nombrePatientLabo;

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setPageNumber(int pageNumer) {
        this.pageNumber = pageNumer;
    }

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public int getNombrePatient() {
		return nombrePatient;
	}

	public void setNombrePatient(int nombrePatient) {
		this.nombrePatient = nombrePatient;
	}

	public int getMontantLabo() {
		return montantLabo;
	}

	public void setMontantLabo(int montantLabo) {
		this.montantLabo = montantLabo;
	}

	public int getNombrePatientLabo() {
		return nombrePatientLabo;
	}

	public void setNombrePatientLabo(int nombrePatientLabo) {
		this.nombrePatientLabo = nombrePatientLabo;
	}

	
	
    
    
}
