package org.sid.saranApp.serviceImpl.rapport;

import org.sid.saranApp.dto.rapport.TopProduitDTO;
import org.sid.saranApp.repository.LigneCommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProduitStatsService {
    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    public List<TopProduitDTO> getTopProduitsVendus(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return ligneCommandeRepository.findTopProduitsVendus(pageable);
    }

    public List<TopProduitDTO> getTopProduitsVendusAvecCategorie(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return ligneCommandeRepository.findTopProduitsVendusAvecCategorie(pageable);
    }

    public List<TopProduitDTO> getTopProduitsVendusByPeriode(
            Date dateDebut,
            Date dateFin,
            int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return ligneCommandeRepository.findTopProduitsVendusByPeriode(
                dateDebut, dateFin, pageable);
    }

    public List<TopProduitDTO> getTopProduitsVendusByBoutique(
            String boutiqueUuid,
            int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return ligneCommandeRepository.findTopProduitsVendusByBoutique(
                boutiqueUuid, pageable);
    }

    public List<TopProduitDTO> getTopProduitsVendusByBoutiqueAndPeriode(
            String boutiqueUuid,
            Date dateDebut,
            Date dateFin,
            int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return ligneCommandeRepository.findTopProduitsVendusByBoutiqueAndPeriode(
                boutiqueUuid, startOfDay(dateDebut), endOfDay(dateFin), pageable);
    }

    private static Date startOfDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    private static Date endOfDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public List<TopProduitDTO> getTopProduitsVendusByCategorie(
            String categorieLibelle,
            int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return ligneCommandeRepository.findTopProduitsVendusByCategorie(
                categorieLibelle, pageable);
    }

    // Méthode pour traiter les résultats de la requête native
    public List<TopProduitDTO> getTopProduitsVendusNative(int limit) {
        List<Object[]> results = ligneCommandeRepository.findTopProduitsVendusNative(limit);
        return results.stream()
                .map(row -> new TopProduitDTO(
                        (String) row[0],           // produitUuid
                        (String) row[1],           // libelleArticle
                        ((Number) row[2]).longValue(), // totalQuantiteVendue
                        ((Number) row[3]).doubleValue() // prixVente
                ))
                .collect(Collectors.toList());
    }
}
