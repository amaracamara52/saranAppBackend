package org.sid.saranApp.controller;

import org.sid.saranApp.dto.TransactionDto;
import org.sid.saranApp.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDto> creerTransaction(@Valid @RequestBody TransactionDto dto) {
        TransactionDto nouvelle = transactionService.addTransaction(dto);
        return new ResponseEntity<>(nouvelle, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/valider")
    public ResponseEntity<TransactionDto> validerTransaction(@PathVariable String uuid) {
        TransactionDto validee = transactionService.validerTransaction(uuid);
        return ResponseEntity.ok(validee);
    }

    @PutMapping("/{uuid}/annuler")
    public ResponseEntity<TransactionDto> annulerTransaction(@PathVariable String uuid) {
        TransactionDto annulee = transactionService.annulerTransaction(uuid);
        return ResponseEntity.ok(annulee);
    }

    @GetMapping("/journalieres")
    public ResponseEntity<List<TransactionDto>> getTransactionsJournalieres() {
        List<TransactionDto> transactions = transactionService.getTransactionsJournalieres();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/totaux/encaissements")
    public ResponseEntity<BigDecimal> getTotalEncaissements() {
        BigDecimal total = transactionService.getTotalEncaissementsJournaliers();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/totaux/decaissements")
    public ResponseEntity<BigDecimal> getTotalDecaissements() {
        BigDecimal total = transactionService.getTotalDecaissementsJournaliers();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/totaux/derniere")
    public ResponseEntity<BigDecimal> getTotalDerniere() {
        BigDecimal total = transactionService.getTotalDerniereTransaction();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/{uuid}")
    public List<TransactionDto> liste(@PathVariable String uuid){
        return transactionService.listeByCaisse(uuid);
    }
}
