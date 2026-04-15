package org.sid.saranApp.service;

import org.sid.saranApp.dto.TransactionDto;
import org.sid.saranApp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService  {
    TransactionDto addTransaction(TransactionDto dto);
    TransactionDto validerTransaction(String uuidTransaction);
    TransactionDto annulerTransaction(String uuidTransaction);
    List<TransactionDto> getTransactionsJournalieres();
    List<TransactionDto> listeByCaisse(String uuid);
    BigDecimal getTotalEncaissementsJournaliers();
    BigDecimal getTotalDecaissementsJournaliers();
    BigDecimal getTotalDerniereTransaction();
}
