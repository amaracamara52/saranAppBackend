package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Publicite extends AbstractDomainClass {
    public String libelle;
    @ManyToOne
    public Categorie categorie;
    public double price;
    private int quantite;
    private String description;
    private boolean publish;
    @ManyToOne
    private Utilisateur utilisateur;
    @OneToMany(mappedBy = "publicite",fetch = FetchType.LAZY)
    private List<PubliciteOperation> publiciteOperations = new ArrayList<PubliciteOperation>();
    @OneToMany(mappedBy = "publicite",fetch = FetchType.LAZY)
    private List<PubliciteParametre> publiciteParametres = new ArrayList<PubliciteParametre>();

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<PubliciteOperation> getPubliciteOperations() {
        return publiciteOperations;
    }

    public void setPubliciteOperations(List<PubliciteOperation> publiciteOperations) {
        this.publiciteOperations = publiciteOperations;
    }

    public List<PubliciteParametre> getPubliciteParametres() {
        return publiciteParametres;
    }

    public void setPubliciteParametres(List<PubliciteParametre> publiciteParametres) {
        this.publiciteParametres = publiciteParametres;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}
