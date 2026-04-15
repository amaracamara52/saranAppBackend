package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.TransactionDto;
import org.sid.saranApp.enume.EnumStatutTransaction;
import org.sid.saranApp.enume.EnumTypeTransaction;
import org.sid.saranApp.exception.BusinessException;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.CommandeVente;
import org.sid.saranApp.model.ModePaiement;
import org.sid.saranApp.model.StoredFile;
import org.sid.saranApp.model.Transaction;
import org.sid.saranApp.repository.CommandeVenteRepository;
import org.sid.saranApp.repository.ModePaiementRepository;
import org.sid.saranApp.repository.StoredFileRepository;
import org.sid.saranApp.repository.TransactionRepository;
import org.sid.saranApp.service.ModePaiementService;
import org.sid.saranApp.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CommandeVenteRepository commandeVenteRepository;
    @Autowired
    private ModePaiementRepository modePaiementRepository;
    @Autowired
    private StoredFileRepository storedFileRepository;
    @Autowired
    private CaisseJournaliereServiceImpl caisseJournaliereService;
    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;

    @Override
    public TransactionDto addTransaction(TransactionDto dto) {
        logger.info("Création d'une nouvelle transaction de type: {}", dto.getTypeTransaction());
        CommandeVente  commandeVente = null;
        // Validation métier
        validerTransaction(dto);

        // Génération de la référence si non fournie
        if (dto.getReference() == null || dto.getReference().isEmpty()) {
            dto.setReference(genererReference(dto.getTypeTransaction()));
        }

        // Conversion DTO vers entité
        Transaction transaction = Mapper.toTransaction(dto);
        transaction.setStatut(EnumStatutTransaction.EN_ATTENTE);

        if( dto.getUuidCommandeVente() != null){
            commandeVente = commandeVenteRepository.findById(dto.getUuidCommandeVente()).orElseThrow(null);
            transaction.setCommandeVente(commandeVente);
            transaction.setBoutique(commandeVente.getBoutique());
        }

        if(dto.getModePaiement().getUuid() != null){
            ModePaiement modePaiement = modePaiementRepository.findById(dto.getModePaiement().getUuid()).orElseThrow(null);
            transaction.setModePaiement(modePaiement);
        }


        if( dto.getUuidReceipt() != null){
            StoredFile receipt = storedFileRepository.findById(dto.getUuidReceipt()).orElseThrow(null);
            transaction.setStoredFile(receipt);
        }

        // Sauvegarde
        transaction = transactionRepository.save(transaction);

        if(transaction.getUuid() != null){
            validerTransaction(transaction.getUuid());
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(commandeVente.getMontantCommadeImage()));
            BigDecimal diff = bigDecimal.subtract(transaction.getMontant());
            logger.info("montant status {}",diff.intValue() == 0);
            logger.info("montant diff {}", diff);
            if(diff.intValue() == 0){
                commandeVente.setPaye(true);
                commandeVente.setMontantCommadeImage(diff.doubleValue());
            }else {
                commandeVente.setPaye(false);
                commandeVente.setMontantCommadeImage(diff.doubleValue());
            }

            commandeVenteRepository.save(commandeVente);
        }

        logger.info("Transaction créée avec l'ID: {} et référence: {}",
                transaction.getUuid(), transaction.getReference());

        return Mapper.toTransactionDto(transaction);
    }



    @Override
    public List<TransactionDto> getTransactionsJournalieres() {
        LocalDateTime debut = LocalDate.now().atStartOfDay();
        LocalDateTime fin = debut.plusDays(1);
        List<Transaction> transactions = transactionRepository.findByDateTransactionBetweenOrderByDateTransactionDesc(debut, fin);
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactions.forEach(t -> transactionDtos.add(Mapper.toTransactionDto(t)));
        return transactionDtos;

    }

    @Override
    public List<TransactionDto> listeByCaisse(String uuid) {
        List<Transaction> transactions = transactionRepository.listeByCaisse(uuid);
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactions.forEach(t -> transactionDtos.add(Mapper.toTransactionDto(t)));
        return transactionDtos;
    }

    @Override
    public BigDecimal getTotalEncaissementsJournaliers() {
        BigDecimal total = transactionRepository.getTotalJournalierParType(
                EnumTypeTransaction.ENCAISSEMENT, EnumStatutTransaction.VALIDEE,utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
        return total != null ? total : BigDecimal.ZERO;


    }

    @Override
    public BigDecimal getTotalDecaissementsJournaliers() {
        BigDecimal total = transactionRepository.getTotalJournalierParType(
                EnumTypeTransaction.DECAISSEMENT, EnumStatutTransaction.VALIDEE,utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTotalDerniereTransaction() {
        BigDecimal total = transactionRepository.getTotalDerniereTransaction();
        return total != null ? total : BigDecimal.ZERO;
    }

    private String genererReference(EnumTypeTransaction type) {
        String prefix = type == EnumTypeTransaction.ENCAISSEMENT ? "ENC" : "DEC";
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return prefix + "-" + timestamp;
    }

    @Override
    public TransactionDto validerTransaction(String transactionId) {
        logger.info("Validation de la transaction ID: {}", transactionId);

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new BusinessException("Transaction non trouvée"));

        if (transaction.getStatut() != EnumStatutTransaction.EN_ATTENTE) {
            throw new BusinessException("Seules les transactions en attente peuvent être validées");
        }

        transaction.setStatut(EnumStatutTransaction.VALIDEE);
        transaction = transactionRepository.save(transaction);

        // Mise à jour de la caisse journalière
        caisseJournaliereService.mettreAJourCaisse(transaction);

        logger.info("Transaction {} validée avec succès", transactionId);

        return Mapper.toTransactionDto(transaction);
    }


    private void validerTransaction(TransactionDto dto) {
        if (dto.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Le montant doit être positif");
        }

        if (dto.getReference() != null &&
                transactionRepository.existsByReference(dto.getReference())) {
            throw new BusinessException("Cette référence existe déjà");
        }
    }

    @Override
    public TransactionDto annulerTransaction(String uuidTransaction) {
        logger.info("Annulation de la transaction ID: {}", uuidTransaction);

        Transaction transaction = transactionRepository.findById(uuidTransaction)
                .orElseThrow(() -> new BusinessException("Transaction non trouvée"));

        if (transaction.getStatut() == EnumStatutTransaction.ANNULEE) {
            throw new BusinessException("La transaction est déjà annulée");
        }

        EnumStatutTransaction ancienStatut = transaction.getStatut();
        transaction.setStatut(EnumStatutTransaction.ANNULEE);
        transaction = transactionRepository.save(transaction);

        // Si la transaction était validée, ajuster la caisse
        if (ancienStatut == EnumStatutTransaction.VALIDEE) {
            caisseJournaliereService.annulerTransactionDansCaisse(transaction);
        }

        logger.info("Transaction {} annulée avec succès", uuidTransaction);

        return Mapper.toTransactionDto(transaction);
    }




}
