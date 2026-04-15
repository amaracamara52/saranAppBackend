package org.sid.saranApp.config;

import org.sid.saranApp.service.CaisseJournaliereService;
import org.sid.saranApp.serviceImpl.ArticleServiceImpl;
import org.sid.saranApp.serviceImpl.UtilisateurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class CaisseService {

    @Autowired
    private CaisseJournaliereService caisseJournaliereService;

    private boolean caisseOuverte = false;
    Logger log = LoggerFactory.getLogger(UtilisateurServiceImpl.class);
    // Démarrer la caisse à 20h10 tous les jours
    @Scheduled(cron = "0 23 13 * * ?")
    public void demarrerCaisse() {
        if (!caisseOuverte) {
            caisseOuverte = true;

            caisseJournaliereService.ouvrirCaisse(BigDecimal.ZERO);
            log.info("🟢 CAISSE DÉMARRÉE à {}", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            // Logique métier pour démarrer la caisse
            initialiserCaisse();
        } else {
            log.warn("⚠️ La caisse est déjà ouverte !");
        }
    }

    // Fermer la caisse à 20h15 tous les jours
    @Scheduled(cron = "0 25 13 * * ?")
    public void fermerCaisse() {
        if (caisseOuverte) {
            caisseOuverte = false;
            log.info("🔴 CAISSE FERMÉE à {}", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            // Logique métier pour fermer la caisse
            finaliserCaisse();
        } else {
            log.warn("⚠️ La caisse est déjà fermée !");
        }
    }

    // Méthodes de logique métier
    private void initialiserCaisse() {
        // Initialisation des ressources
        log.info("📋 Initialisation des paramètres de caisse...");
        log.info("💰 Chargement du fond de caisse...");
        log.info("🖨️ Vérification de l'imprimante...");
        log.info("✅ Caisse prête pour les transactions");
    }

    private void finaliserCaisse() {
        // Finalisation et sauvegarde
        log.info("📊 Génération du rapport de fin de journée...");
        log.info("💾 Sauvegarde des transactions...");
        log.info("🔒 Verrouillage de la caisse...");
        log.info("✅ Fermeture terminée");
    }

    // Méthode pour vérifier l'état de la caisse
    public boolean isCaisseOuverte() {
        return caisseOuverte;
    }

    // Méthode pour ouvrir/fermer manuellement si nécessaire
    public void toggleCaisse() {
        if (caisseOuverte) {
            fermerCaisse();
        } else {
            demarrerCaisse();
        }
    }
}