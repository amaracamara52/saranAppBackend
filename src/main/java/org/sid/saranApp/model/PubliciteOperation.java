package org.sid.saranApp.model;

import org.sid.saranApp.enume.EnumStatutOnlineOrder;

import javax.persistence.*;

@Entity
public class PubliciteOperation extends  AbstractDomainClass{
    @ManyToOne
    private Publicite publicite;
    @Column(name = "is_like")  // Remplace "like" par "is_like"
    private boolean like;
    private boolean favorite;
    private boolean interested;

    @ManyToOne
    private Utilisateur utilisateur;
    @Enumerated(EnumType.STRING)
    private EnumStatutOnlineOrder enumStatutOnlineOrder;

    public Publicite getPublicite() {
        return publicite;
    }

    public void setPublicite(Publicite publicite) {
        this.publicite = publicite;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isInterested() {
        return interested;
    }

    public void setInterested(boolean interested) {
        this.interested = interested;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public EnumStatutOnlineOrder getEnumStatutOnlineOrder() {
        return enumStatutOnlineOrder;
    }

    public void setEnumStatutOnlineOrder(EnumStatutOnlineOrder enumStatutOnlineOrder) {
        this.enumStatutOnlineOrder = enumStatutOnlineOrder;
    }
}
