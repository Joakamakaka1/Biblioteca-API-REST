package com.proyecto1t.bibliotaca_api.repository;

import com.proyecto1t.bibliotaca_api.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
