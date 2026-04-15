package org.sid.saranApp.dto.rapport;

public class TopClientDTO {
    private String clientUuid;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private Long nombreCommandes;
    private Double montantTotal;
    private Double montantMoyen;
    private String boutiqueUuid;

    // Constructeur pour les statistiques de base
    public TopClientDTO(String clientUuid, String nom, String prenom, String email,
                        Long nombreCommandes, Double montantTotal) {
        this.clientUuid = clientUuid;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.nombreCommandes = nombreCommandes;
        this.montantTotal = montantTotal;
        this.montantMoyen = nombreCommandes > 0 ? montantTotal / nombreCommandes : 0.0;
    }

    // Constructeur complet avec phone et boutique
    public TopClientDTO(String clientUuid, String nom, String prenom, String email, String phone,
                        Long nombreCommandes, Double montantTotal, String boutiqueUuid) {
        this.clientUuid = clientUuid;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
        this.nombreCommandes = nombreCommandes;
        this.montantTotal = montantTotal;
        this.montantMoyen = nombreCommandes > 0 ? montantTotal / nombreCommandes : 0.0;
        this.boutiqueUuid = boutiqueUuid;
    }

    // Getters et setters
    public String getClientUuid() { return clientUuid; }
    public void setClientUuid(String clientUuid) { this.clientUuid = clientUuid; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Long getNombreCommandes() { return nombreCommandes; }
    public void setNombreCommandes(Long nombreCommandes) { this.nombreCommandes = nombreCommandes; }

    public Double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(Double montantTotal) { this.montantTotal = montantTotal; }

    public Double getMontantMoyen() { return montantMoyen; }
    public void setMontantMoyen(Double montantMoyen) { this.montantMoyen = montantMoyen; }

    public String getBoutiqueUuid() { return boutiqueUuid; }
    public void setBoutiqueUuid(String boutiqueUuid) { this.boutiqueUuid = boutiqueUuid; }

    // Méthode utilitaire pour obtenir le nom complet
    public String getNomComplet() {
        return (prenom != null ? prenom + " " : "") + (nom != null ? nom : "");
    }
}