package com.proyecto1t.bibliotaca_api.service;

import com.proyecto1t.bibliotaca_api.dto.LoanDTO;
import com.proyecto1t.bibliotaca_api.exceptions.NotFoundException;
import com.proyecto1t.bibliotaca_api.model.Book;
import com.proyecto1t.bibliotaca_api.model.Loan;
import com.proyecto1t.bibliotaca_api.model.User;
import com.proyecto1t.bibliotaca_api.repository.LoanRepository;
import com.proyecto1t.bibliotaca_api.utils.Mapper;
import com.proyecto1t.bibliotaca_api.utils.StringToLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private StringToLong stringToLong;

    public List<LoanDTO> findAll() {
        List<Loan> loans = loanRepository.findAll();
        if (loans.isEmpty()) {
            throw new NotFoundException("Loans not found");
        }

        List<LoanDTO> loansDTOs = new ArrayList<>();
        loans.forEach(loan1 -> loansDTOs.add(mapper.toLoanDTO(loan1)));
        return loansDTOs;
    }

    public LoanDTO findById(String id) {
        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Loan not found");
        }
        Loan loan = loanRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Loan not found"));

        return mapper.toLoanDTO(loan);
    }

    public LoanDTO createLoan(LoanDTO loanDTO, User userId, Book bookId) {
        if (loanDTO == null) {
            throw new NotFoundException("Loan not found");
        }

        if (userId == null) {
            throw new NotFoundException("User not found");
        }

        if (bookId == null) {
            throw new NotFoundException("Book not found");
        }

        Loan loan = mapper.toLoanEntity(loanDTO, userId, bookId);
        loan = loanRepository.save(loan);
        return mapper.toLoanDTO(loan);
    }

    public LoanDTO updateLoan(String id, LoanDTO loanDTO, User userId, Book bookId) {
        if (loanDTO == null) {
            throw new NotFoundException("Loan not found");
        }

        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Loan not found");
        }

        if (userId == null) {
            throw new NotFoundException("User not found");
        }

        if (bookId == null) {
            throw new NotFoundException("Book not found");
        }

        Loan existingLoan = loanRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Loan not found"));

        Loan loan = mapper.toLoanEntity(loanDTO, userId, bookId);
        loan.setId(existingLoan.getId());
        loan = loanRepository.save(loan);
        return mapper.toLoanDTO(loan);
    }

    public void deleteLoan(String id) {
        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Loan not found");
        }

        Loan loan = loanRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Loan not found"));

        loanRepository.deleteById(loan.getId());
    }
}
