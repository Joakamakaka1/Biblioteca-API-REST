package com.proyecto1t.bibliotaca_api.service;

import com.proyecto1t.bibliotaca_api.dto.LoanDTO;
import com.proyecto1t.bibliotaca_api.exceptions.DuplicateException;
import com.proyecto1t.bibliotaca_api.exceptions.NotFoundException;
import com.proyecto1t.bibliotaca_api.model.Book;
import com.proyecto1t.bibliotaca_api.model.Loan;
import com.proyecto1t.bibliotaca_api.model.User;
import com.proyecto1t.bibliotaca_api.repository.BookRepository;
import com.proyecto1t.bibliotaca_api.repository.LoanRepository;
import com.proyecto1t.bibliotaca_api.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

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
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Loan not found");
        }
        Loan loan = loanRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Loan not found"));

        return mapper.toLoanDTO(loan);
    }

    public LoanDTO createLoan(LoanDTO loanDTO) {
        if (loanDTO == null) {
            throw new NotFoundException("Loan not found");
        }

        User userId = userRepository.findByUsername(loanDTO.getUserId().getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Book bookId = bookRepository.findByIsbn(loanDTO.getBookId().getIsbn())
                .orElseThrow(() -> new NotFoundException("Book not found. Check isbn"));

        Loan loan = mapper.toLoanEntity(loanDTO, userId, bookId);
        loan = loanRepository.save(loan);
        return mapper.toLoanDTO(loan);
    }

    public LoanDTO updateLoan(String id, LoanDTO loanDTO) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Loan not found");
        }

        User user = userRepository.findByUsername(loanDTO.getUserId().getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Book book = bookRepository.findByIsbn(loanDTO.getBookId().getIsbn())
                .orElseThrow(() -> new NotFoundException("Book not found. Check isbn"));

        Loan existingLoan = loanRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new DuplicateException("Loan not found"));

        existingLoan.setLoanDate(loanDTO.getLoanDate());
        existingLoan.setReturnDate(loanDTO.getReturnDate());
        existingLoan.setUser(user);
        existingLoan.setBook(book);

        Loan loan = loanRepository.save(existingLoan);
        return mapper.toLoanDTO(loan);
    }

    public void deleteLoan(String id) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Loan not found");
        }

        Loan loan = loanRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Loan not found"));

        loanRepository.deleteById(loan.getId());
    }
}
