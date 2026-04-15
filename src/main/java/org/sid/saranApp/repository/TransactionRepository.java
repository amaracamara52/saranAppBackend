package org.sid.saranApp.repository;

import org.sid.saranApp.enume.EnumStatutTransaction;
import org.sid.saranApp.enume.EnumTypeTransaction;
import org.sid.saranApp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,String> {
    List<Transaction> findByTypeAndStatutOrderByDateTransactionDesc(
            EnumTypeTransaction type, EnumStatutTransaction statut);

    List<Transaction> findByDateTransactionBetweenOrderByDateTransactionDesc(
            LocalDateTime debut, LocalDateTime fin);

    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.type = :type " +
            "AND t.statut = :statut AND t.boutique.uuid=:boutique")
    BigDecimal getTotalJournalierParType(@Param("type") EnumTypeTransaction type,
                                         @Param("statut") EnumStatutTransaction statut,
                                         @Param("boutique") String boutiqueUuid);

    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.type = :type " +
            "AND t.statut = :statut AND t.dateTransaction BETWEEN :debut AND :fin")
    BigDecimal getTotalParPeriodeEtType(@Param("type") EnumTypeTransaction type,
                                        @Param("statut") EnumStatutTransaction statut,
                                        @Param("debut") LocalDateTime debut,
                                        @Param("fin") LocalDateTime fin);

    boolean existsByReference(String reference);



    @Query(value="WITH derniere_date AS (\n" +
            "    SELECT MAX(DATE(date_transaction)) as last_date\n" +
            "    FROM public.\"transaction\"\n" +
            "    WHERE DATE(date_transaction) < CURRENT_DATE\n" +
            "    AND is_delete = false\n" +
            ")\n" +
            "SELECT \n" +
            "    SUM(montant) as somme_total_encaissement\n" +
            "   -- COUNT(*) as nombre_transactions,\n" +
            "   -- dd.last_date as date_transaction\n" +
            "FROM public.\"transaction\" t\n" +
            "CROSS JOIN derniere_date dd\n" +
            "WHERE DATE(t.date_transaction) = dd.last_date\n" +
            "AND t.type = 'ENCAISSEMENT'\n" +
            "AND t.is_delete = false\n" +
            "GROUP BY dd.last_date",nativeQuery = true)
    BigDecimal getTotalDerniereTransaction();


    @Query("select t from Transaction t where t.caisse.uuid=:x")
    List<Transaction> listeByCaisse(@Param("x") String uuid);



}
