package org.sid.saranApp.serviceImpl.rapport;

import org.sid.saranApp.dto.rapport.StatistiquesGeneralesDTO;
import org.sid.saranApp.repository.CommandeVenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StatistiquesVentesService {

    @Autowired
    private CommandeVenteRepository commandeVenteRepository;

    public StatistiquesGeneralesDTO getStatistiquesGenerales() {
        return commandeVenteRepository.getStatistiquesGenerales();
    }

    public StatistiquesGeneralesDTO getStatistiquesCompletes() {
        return commandeVenteRepository.getStatistiquesCompletes();
    }

    public StatistiquesGeneralesDTO getStatistiquesParPeriode(Date dateDebut, Date dateFin) {
        return commandeVenteRepository.getStatistiquesParPeriode(dateDebut, dateFin);
    }

    public StatistiquesGeneralesDTO getStatistiquesParBoutique(String boutiqueUuid) {
        return commandeVenteRepository.getStatistiquesParBoutique(boutiqueUuid);
    }

    // Méthode pour traiter les résultats de la requête native
    public StatistiquesGeneralesDTO getStatistiquesNative() {
        Object[] result = commandeVenteRepository.getStatistiquesNative();
        if (result != null) {
            return new StatistiquesGeneralesDTO(
                    result[0] != null ? ((Number) result[0]).doubleValue() : 0.0,  // montantTotal
                    result[1] != null ? ((Number) result[1]).longValue() : 0L,     // nombreTotalCommandes
                    result[2] != null ? ((Number) result[2]).longValue() : 0L,     // clientTotal
                    result[4] != null ? ((Number) result[4]).longValue() : 0L,     // commandesPayees
                    result[5] != null ? ((Number) result[5]).longValue() : 0L,     // commandesNonPayees
                    0.0, // À calculer séparément si nécessaire
                    0.0  // À calculer séparément si nécessaire
            );
        }
        return new StatistiquesGeneralesDTO(0.0, 0L, 0L);
    }

    // Statistiques par type de commande
    public Map<String, StatistiquesGeneralesDTO> getStatistiquesParTypeCommande() {
        List<Object[]> results = commandeVenteRepository.getStatistiquesParTypeCommande();
        Map<String, StatistiquesGeneralesDTO> statistiques = new HashMap<>();

        for (Object[] row : results) {
            String typeCommande = (String) row[0];
            Double montantTotal = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
            Long nombreCommandes = row[2] != null ? ((Number) row[2]).longValue() : 0L;
            Long clientsUniques = row[3] != null ? ((Number) row[3]).longValue() : 0L;

            statistiques.put(typeCommande,
                    new StatistiquesGeneralesDTO(montantTotal, nombreCommandes, clientsUniques));
        }

        return statistiques;
    }

    // Évolution des ventes par mois
    public List<Map<String, Object>> getEvolutionVentesParMois(int derniersMois) {
        LocalDateTime dateDebut = LocalDateTime.now().minusMonths(derniersMois);
        List<Object[]> results = commandeVenteRepository.getEvolutionVentesParMois(dateDebut);

        return results.stream()
                .map(row -> {
                    Map<String, Object> mois = new HashMap<>();
                    mois.put("mois", row[0]);
                    mois.put("montantTotal", row[1] != null ? ((Number) row[1]).doubleValue() : 0.0);
                    mois.put("nombreCommandes", row[2] != null ? ((Number) row[2]).longValue() : 0L);
                    mois.put("clientsUniques", row[3] != null ? ((Number) row[3]).longValue() : 0L);
                    return mois;
                })
                .collect(Collectors.toList());
    }

    // Comparaison des périodes
    public Map<String, Object> comparerPeriodes(Date periode1Debut, Date periode1Fin,
                                                Date periode2Debut, Date periode2Fin) {
        StatistiquesGeneralesDTO stats1 = getStatistiquesParPeriode(periode1Debut, periode1Fin);
        StatistiquesGeneralesDTO stats2 = getStatistiquesParPeriode(periode2Debut, periode2Fin);

        Map<String, Object> comparaison = new HashMap<>();
        comparaison.put("periode1", stats1);
        comparaison.put("periode2", stats2);

        // Calcul des évolutions en pourcentage
        if (stats2.getMontantTotal() > 0) {
            Double evolutionMontant = ((stats1.getMontantTotal() - stats2.getMontantTotal()) / stats2.getMontantTotal()) * 100;
            comparaison.put("evolutionMontant", evolutionMontant);
        }

        if (stats2.getNombreTotalCommandes() > 0) {
            Double evolutionCommandes = ((stats1.getNombreTotalCommandes().doubleValue() - stats2.getNombreTotalCommandes().doubleValue()) / stats2.getNombreTotalCommandes().doubleValue()) * 100;
            comparaison.put("evolutionCommandes", evolutionCommandes);
        }

        return comparaison;
    }
}
