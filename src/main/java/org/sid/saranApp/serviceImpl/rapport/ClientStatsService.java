package org.sid.saranApp.serviceImpl.rapport;

import org.sid.saranApp.dto.rapport.TopClientDTO;
import org.sid.saranApp.repository.CommandeVenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ClientStatsService {

    @Autowired
    private CommandeVenteRepository commandeVenteRepository;

    public List<TopClientDTO> getTopClientsByNombreCommandes(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return commandeVenteRepository.findTopClientsByNombreCommandes(pageable);
    }

    public List<TopClientDTO> getTopClientsByMontantTotal(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return commandeVenteRepository.findTopClientsByMontantTotal(pageable);
    }

    public List<TopClientDTO> getTopClientsByPeriode(
            Date dateDebut,
            Date dateFin,
            int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return commandeVenteRepository.findTopClientsByPeriode(dateDebut, dateFin, pageable);
    }

    public List<TopClientDTO> getTopClientsByBoutique(String boutiqueUuid, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return commandeVenteRepository.findTopClientsByBoutique(boutiqueUuid, pageable);
    }

    public List<TopClientDTO> getTopClientsFideles(
            LocalDateTime dateDebut,
            Long nombreCommandesMin,
            int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return commandeVenteRepository.findTopClientsFideles(
                dateDebut, nombreCommandesMin, pageable);
    }

    public List<TopClientDTO> getTopClientsCommandesPayees(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return commandeVenteRepository.findTopClientsCommandesPayees(pageable);
    }

    // Méthode pour traiter les résultats de la requête native
    public List<TopClientDTO> getTopClientsNative(int limit) {
        List<Object[]> results = commandeVenteRepository.findTopClientsNative(limit);
        return results.stream()
                .map(row -> new TopClientDTO(
                        (String) row[0],           // clientUuid
                        (String) row[1],           // nom
                        (String) row[2],           // prenom
                        (String) row[3],           // email
                        (String) row[4],           // phone
                        ((Number) row[5]).longValue(),  // nombreCommandes
                        ((Number) row[6]).doubleValue(), // montantTotal
                        null                       // boutiqueUuid
                ))
                .collect(Collectors.toList());
    }

    // Méthode pour obtenir les statistiques détaillées d'un client
    @Query("SELECT new org.sid.saranApp.dto.rapport.TopClientDTO(" +
            "c.uuid, " +
            "c.nom, " +
            "c.prenom, " +
            "c.email, " +
            "c.phone, " +
            "COUNT(cv.uuid), " +
            "SUM(cv.montantCommande), " +
            "c.boutiqueUuid) " +
            "FROM CommandeVente cv " +
            "JOIN Client c ON cv.clientUuid = c.uuid " +
            "WHERE cv.isDelete = false AND c.isDelete = false " +
            "AND c.uuid = :clientUuid " +
            "GROUP BY c.uuid, c.nom, c.prenom, c.email, c.phone, c.boutiqueUuid")
    public TopClientDTO getStatistiquesClient(@Param("clientUuid") String clientUuid) {
        // Cette méthode serait dans le repository, je la mets ici pour l'exemple
        return null; // Implémentation à faire
    }
}
