package org.sid.saranApp.controller.rapport;

import org.sid.saranApp.dto.rapport.StatistiquesGeneralesDTO;
import org.sid.saranApp.serviceImpl.rapport.StatistiquesVentesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats/ventes")
public class StatistiquesVentesController {

    @Autowired
    private StatistiquesVentesService statistiquesVentesService;

    @GetMapping("/generales")
    public ResponseEntity<StatistiquesGeneralesDTO> getStatistiquesGenerales() {
        StatistiquesGeneralesDTO stats = statistiquesVentesService.getStatistiquesGenerales();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/completes")
    public ResponseEntity<StatistiquesGeneralesDTO> getStatistiquesCompletes() {
        StatistiquesGeneralesDTO stats = statistiquesVentesService.getStatistiquesCompletes();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/periode")
    public ResponseEntity<StatistiquesGeneralesDTO> getStatistiquesParPeriode(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin) {
        StatistiquesGeneralesDTO stats = statistiquesVentesService.getStatistiquesParPeriode(dateDebut, dateFin);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/boutique/{boutiqueUuid}")
    public ResponseEntity<StatistiquesGeneralesDTO> getStatistiquesParBoutique(@PathVariable String boutiqueUuid) {
        StatistiquesGeneralesDTO stats = statistiquesVentesService.getStatistiquesParBoutique(boutiqueUuid);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/par-type")
    public ResponseEntity<Map<String, StatistiquesGeneralesDTO>> getStatistiquesParTypeCommande() {
        Map<String, StatistiquesGeneralesDTO> stats = statistiquesVentesService.getStatistiquesParTypeCommande();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/evolution/{derniersMois}")
    public ResponseEntity<List<Map<String, Object>>> getEvolutionVentesParMois(@PathVariable int derniersMois) {
        List<Map<String, Object>> evolution = statistiquesVentesService.getEvolutionVentesParMois(derniersMois);
        return ResponseEntity.ok(evolution);
    }

    @GetMapping("/comparer")
    public ResponseEntity<Map<String, Object>> comparerPeriodes(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date periode1Debut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date periode1Fin,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date periode2Debut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date periode2Fin) {
        Map<String, Object> comparaison = statistiquesVentesService.comparerPeriodes(
                periode1Debut, periode1Fin, periode2Debut, periode2Fin);
        return ResponseEntity.ok(comparaison);
    }

    @GetMapping("/native")
    public ResponseEntity<StatistiquesGeneralesDTO> getStatistiquesNative() {
        StatistiquesGeneralesDTO stats = statistiquesVentesService.getStatistiquesNative();
        return ResponseEntity.ok(stats);
    }

    // Endpoint pratique pour obtenir les stats du jour
//    @GetMapping("/aujourd-hui")
//    public ResponseEntity<StatistiquesGeneralesDTO> getStatistiquesAujourdhui() {
//        Date debutJour = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
//        LocalDateTime finJour = debutJour.plusDays(1).minusSeconds(1);
//        StatistiquesGeneralesDTO stats = statistiquesVentesService.getStatistiquesParPeriode(debutJour, finJour);
//        return ResponseEntity.ok(stats);
//    }

    // Endpoint pour les stats de la semaine
//    @GetMapping("/cette-semaine")
//    public ResponseEntity<StatistiquesGeneralesDTO> getStatistiquesSemaine() {
//        LocalDateTime debutSemaine = LocalDateTime.now().with(DayOfWeek.MONDAY).truncatedTo(ChronoUnit.DAYS);
//        LocalDateTime finSemaine = debutSemaine.plusDays(7).minusSeconds(1);
//        StatistiquesGeneralesDTO stats = statistiquesVentesService.getStatistiquesParPeriode(debutSemaine, finSemaine);
//        return ResponseEntity.ok(stats);
//    }

    // Endpoint pour les stats du mois
//    @GetMapping("/ce-mois")
//    public ResponseEntity<StatistiquesGeneralesDTO> getStatistiquesMois() {
//        LocalDateTime debutMois = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS);
//        LocalDateTime finMois = debutMois.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1).minusSeconds(1);
//        StatistiquesGeneralesDTO stats = statistiquesVentesService.getStatistiquesParPeriode(debutMois, finMois);
//        return ResponseEntity.ok(stats);
//    }
}
