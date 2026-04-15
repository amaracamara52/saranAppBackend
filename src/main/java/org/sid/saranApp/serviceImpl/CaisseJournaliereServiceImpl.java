package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.CaisseJournaliereDto;
import org.sid.saranApp.enume.EnumStatutCaisse;
import org.sid.saranApp.enume.EnumStatutTransaction;
import org.sid.saranApp.enume.EnumTypeTransaction;
import org.sid.saranApp.exception.BusinessException;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.CaisseJournaliere;
import org.sid.saranApp.model.Transaction;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.CaisseJournaliereRepository;
import org.sid.saranApp.service.CaisseJournaliereService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CaisseJournaliereServiceImpl implements CaisseJournaliereService {
    Logger logger = LoggerFactory.getLogger(CaisseJournaliereServiceImpl.class);
    @Autowired
    private CaisseJournaliereRepository  caisseJournaliereRepository;
    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;
    @Override
    public CaisseJournaliereDto ouvrirCaisse(BigDecimal soldeOuverture) {
        LocalDate aujourd = LocalDate.now();
        Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
        if (caisseJournaliereRepository.existsByDateCaisseAndBoutique(aujourd,user.getBoutique())) {
            throw new BusinessException("La caisse est déjà ouverte pour aujourd'hui");
        }

        CaisseJournaliere caisse = new CaisseJournaliere();
        caisse.setDateCaisse(aujourd);
        caisse.setSoldeOuverture(soldeOuverture);
        caisse.setTotalEncaissement(BigDecimal.ZERO);
        caisse.setTotalDecaissement(BigDecimal.ZERO);
        caisse.setStatutCaisse(EnumStatutCaisse.OUVERT);
        caisse.setUtilisateur(user);
        caisse.setBoutique(user.getBoutique());

        caisse = caisseJournaliereRepository.save(caisse);

        logger.info("Caisse ouverte pour le {} avec un solde d'ouverture de {}",
                aujourd, soldeOuverture);

        return Mapper.toCaisseJournaliere(caisse);
    }

    @Override
    public CaisseJournaliereDto fermerCaisse(BigDecimal soldeFermeture) {
        Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
        CaisseJournaliere caisse = caisseJournaliereRepository.findByStatutCaisseAndBoutique(EnumStatutCaisse.OUVERT,user.getBoutique())
                .orElseThrow(() -> new BusinessException("Aucune caisse ouverte trouvée"));

        caisse.setSoldeFermeture(soldeFermeture);
        caisse.setStatutCaisse(EnumStatutCaisse.FERMER);
        caisse.setDateFermeture(LocalDateTime.now());

        caisse = caisseJournaliereRepository.save(caisse);

        logger.info("Caisse fermée pour le {} avec un solde de fermeture de {}",
                caisse.getDateCaisse(), soldeFermeture);

        return Mapper.toCaisseJournaliere(caisse);
    }




    public void mettreAJourCaisse(Transaction transaction) {
        CaisseJournaliere caisse = getCaisseOuverte();

        if (transaction.getType() == EnumTypeTransaction.ENCAISSEMENT) {
            caisse.setTotalEncaissement(
                    caisse.getTotalEncaissement().add(transaction.getMontant()));
        } else {
            caisse.setTotalDecaissement(
                    caisse.getTotalDecaissement().add(transaction.getMontant()));
        }

        caisseJournaliereRepository.save(caisse);

        logger.info("Caisse mise à jour - {} de {}",
                transaction.getType(), transaction.getMontant());
    }

    public void annulerTransactionDansCaisse(Transaction transaction) {
        CaisseJournaliere caisse = getCaisseOuverte();

        if (transaction.getType() == EnumTypeTransaction.ENCAISSEMENT) {
            caisse.setTotalEncaissement(
                    caisse.getTotalEncaissement().subtract(transaction.getMontant()));
        } else {
            caisse.setTotalDecaissement(
                    caisse.getTotalDecaissement().subtract(transaction.getMontant()));
        }

        caisseJournaliereRepository.save(caisse);

        logger.info("Transaction annulée dans la caisse - {} de {}",
                transaction.getType(), transaction.getMontant());
    }

    @Override
    @Transactional
    public CaisseJournaliereDto getCaisseActuelle() {
        CaisseJournaliere caisse = getCaisseOuverte();
        return Mapper.toCaisseJournaliere(caisse);
    }

    @Override
    public List<CaisseJournaliereDto> listes() {
        List<CaisseJournaliere> caisseJournalieres = caisseJournaliereRepository.findAll();
        List<CaisseJournaliereDto> caisseJournaliereDtos = new ArrayList<>();
        caisseJournalieres.forEach(caisse -> caisseJournaliereDtos.add(Mapper.toCaisseJournaliere(caisse)));
        return caisseJournaliereDtos;
    }

    private CaisseJournaliere getCaisseOuverte() {
        Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
        return caisseJournaliereRepository.findByStatutCaisseAndBoutique(EnumStatutCaisse.OUVERT,user.getBoutique())
                .orElseThrow(() -> new BusinessException("Aucune caisse ouverte. Veuillez ouvrir la caisse d'abord."));
    }
}
