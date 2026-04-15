package org.sid.saranApp.repository;

import org.sid.saranApp.enume.EnumStatutCaisse;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.CaisseJournaliere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CaisseJournaliereRepository extends JpaRepository<CaisseJournaliere,String> {

    Optional<CaisseJournaliere> findByDateCaisseAndBoutique(LocalDate date,Boutique boutique);

    Optional<CaisseJournaliere> findByStatutCaisseAndBoutique(EnumStatutCaisse statut, Boutique boutique);

    boolean existsByDateCaisseAndBoutique(LocalDate date,Boutique boutique);
}
