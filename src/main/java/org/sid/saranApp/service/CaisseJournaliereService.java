package org.sid.saranApp.service;

import org.sid.saranApp.dto.CaisseJournaliereDto;

import java.math.BigDecimal;
import java.util.List;

public interface CaisseJournaliereService {
    CaisseJournaliereDto ouvrirCaisse(BigDecimal soldeOuverture);
    CaisseJournaliereDto fermerCaisse(BigDecimal soldeFermeture);
    CaisseJournaliereDto getCaisseActuelle();
    List<CaisseJournaliereDto> listes();
}
