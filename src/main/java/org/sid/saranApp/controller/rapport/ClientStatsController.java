package org.sid.saranApp.controller.rapport;

import org.sid.saranApp.dto.rapport.TopClientDTO;
import org.sid.saranApp.serviceImpl.rapport.ClientStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/stats/clients")
public class ClientStatsController {

    @Autowired
    private ClientStatsService clientStatsService;

    @GetMapping("/top-par-commandes")
    public ResponseEntity<List<TopClientDTO>> getTopClientsByNombreCommandes(
            @RequestParam(defaultValue = "10") int limit) {
        List<TopClientDTO> topClients = clientStatsService.getTopClientsByNombreCommandes(limit);
        return ResponseEntity.ok(topClients);
    }

    @GetMapping("/top-par-montant")
    public ResponseEntity<List<TopClientDTO>> getTopClientsByMontantTotal(
            @RequestParam(defaultValue = "10") int limit) {
        List<TopClientDTO> topClients = clientStatsService.getTopClientsByMontantTotal(limit);
        return ResponseEntity.ok(topClients);
    }

    @GetMapping("/top-par-periode")
    public ResponseEntity<List<TopClientDTO>> getTopClientsByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateFin,
            @RequestParam(defaultValue = "10") int limit) {
        List<TopClientDTO> topClients = clientStatsService.getTopClientsByPeriode(
                dateDebut, dateFin, limit);
        return ResponseEntity.ok(topClients);
    }

    @GetMapping("/top-par-boutique/{boutiqueUuid}")
    public ResponseEntity<List<TopClientDTO>> getTopClientsByBoutique(
            @PathVariable String boutiqueUuid,
            @RequestParam(defaultValue = "10") int limit) {
        List<TopClientDTO> topClients = clientStatsService.getTopClientsByBoutique(
                boutiqueUuid, limit);
        return ResponseEntity.ok(topClients);
    }

    @GetMapping("/top-fideles")
    public ResponseEntity<List<TopClientDTO>> getTopClientsFideles(
            @RequestParam(defaultValue = "30") int derniersDays,
            @RequestParam(defaultValue = "3") Long nombreCommandesMin,
            @RequestParam(defaultValue = "10") int limit) {
        LocalDateTime dateDebut = LocalDateTime.now().minusDays(derniersDays);
        List<TopClientDTO> topClients = clientStatsService.getTopClientsFideles(
                dateDebut, nombreCommandesMin, limit);
        return ResponseEntity.ok(topClients);
    }

    @GetMapping("/top-payees")
    public ResponseEntity<List<TopClientDTO>> getTopClientsCommandesPayees(
            @RequestParam(defaultValue = "10") int limit) {
        List<TopClientDTO> topClients = clientStatsService.getTopClientsCommandesPayees(limit);
        return ResponseEntity.ok(topClients);
    }

    @GetMapping("/top-native")
    public ResponseEntity<List<TopClientDTO>> getTopClientsNative(
            @RequestParam(defaultValue = "10") int limit) {
        List<TopClientDTO> topClients = clientStatsService.getTopClientsNative(limit);
        return ResponseEntity.ok(topClients);
    }

    // Endpoint pour obtenir un client spécifique avec ses stats
    @GetMapping("/{clientUuid}/statistiques")
    public ResponseEntity<TopClientDTO> getStatistiquesClient(@PathVariable String clientUuid) {
        TopClientDTO clientStats = clientStatsService.getStatistiquesClient(clientUuid);
        return ResponseEntity.ok(clientStats);
    }
}
