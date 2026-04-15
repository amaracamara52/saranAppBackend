package org.sid.saranApp.dto.rapport;

import java.util.ArrayList;
import java.util.List;

public class RapportVenteDto {

    private CritereFiltreDto critereFiltreDto;
    private double montantVente;
    private int nombreCommande;
    private int clientActif;
    private int pannierMoyen;

    private List<TopProduitDTO> topProduitDTOS = new ArrayList<>();
    private  List<TopClientDTO> topClients = new ArrayList<>();
    private  List<DetailCommandeDto> detailCommandeDtos = new ArrayList<>();

    public CritereFiltreDto getCritereFiltreDto() {
        return critereFiltreDto;
    }

    public void setCritereFiltreDto(CritereFiltreDto critereFiltreDto) {
        this.critereFiltreDto = critereFiltreDto;
    }

    public double getMontantVente() {
        return montantVente;
    }

    public void setMontantVente(double montantVente) {
        this.montantVente = montantVente;
    }

    public int getNombreCommande() {
        return nombreCommande;
    }

    public void setNombreCommande(int nombreCommande) {
        this.nombreCommande = nombreCommande;
    }

    public int getClientActif() {
        return clientActif;
    }

    public void setClientActif(int clientActif) {
        this.clientActif = clientActif;
    }

    public int getPannierMoyen() {
        return pannierMoyen;
    }

    public void setPannierMoyen(int pannierMoyen) {
        this.pannierMoyen = pannierMoyen;
    }

    public List<TopProduitDTO> getTopProduitVenteDtos() {
        return topProduitDTOS;
    }

    public void setTopProduitVenteDtos(List<TopProduitDTO> topProduitDTOS) {
        this.topProduitDTOS = topProduitDTOS;
    }

    public List<TopClientDTO> getTopClients() {
        return topClients;
    }

    public void setTopClients(List<TopClientDTO> topClients) {
        this.topClients = topClients;
    }

    public List<DetailCommandeDto> getDetailCommandeDtos() {
        return detailCommandeDtos;
    }

    public void setDetailCommandeDtos(List<DetailCommandeDto> detailCommandeDtos) {
        this.detailCommandeDtos = detailCommandeDtos;
    }
}
