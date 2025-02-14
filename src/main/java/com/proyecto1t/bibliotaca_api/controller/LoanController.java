package com.proyecto1t.bibliotaca_api.controller;

import com.proyecto1t.bibliotaca_api.dto.LoanDTO;
import com.proyecto1t.bibliotaca_api.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping("/") // -> http://localhost:8080/loan/
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> loans = loanService.findAll();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping("/{id}") // -> http://localhost:8080/loan/1
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable String id) {
        LoanDTO loan = loanService.findById(id);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @PostMapping("/") // -> http://localhost:8080/loan/
    public ResponseEntity<LoanDTO> createLoan(@Valid @RequestBody LoanDTO loan) {
        LoanDTO createdLoan = loanService.createLoan(loan);
        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // -> http://localhost:8080/loan/1
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable String id, @Valid @RequestBody LoanDTO loan) {
        LoanDTO updatedLoan = loanService.updateLoan(id, loan);
        return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // -> http://localhost:8080/loan/1
    public ResponseEntity<Void> deleteLoan(@PathVariable String id) {
        loanService.deleteLoan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
