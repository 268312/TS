package com.example.ts.service;

import com.example.ts.infrastructure.entity.LoanEntity;
import com.example.ts.infrastructure.entity.UserEntity;
import com.example.ts.infrastructure.repository.BookRepository;
import com.example.ts.infrastructure.repository.LoanRepository;
import com.example.ts.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;

    }

    public LoanEntity getOne(Integer id) {
        return loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found."));
    }

    public LoanEntity addLoan(LoanEntity loan) {
        return loanRepository.save(loan);
    }

    public LoanEntity borrowBook(Integer userId, Integer bookId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        LoanEntity loan = new LoanEntity();
        loan.setUser(user);
        loan.setLoanDate(new Date(System.currentTimeMillis()));
        // Ustawienie książki (bookId przekazywane z żądania)
        loan.setBook(bookRepository.findById(bookId));
        return loanRepository.save(loan);
    }
    public void returnBook(Integer loanId) {
        // Pobierz wypożyczenie
        LoanEntity loan = loanRepository.findById(loanId).orElse(null);
        if (loan == null) {
            throw new IllegalArgumentException("Loan not found");
        }

        // Ustaw datę zwrotu na obecną
        loan.setReturnDate(new Date(System.currentTimeMillis()));

        // Zapisz zmiany
        loanRepository.save(loan);
    }

    public List<LoanEntity> getLoanHistory(Integer userId) {
        // Pobierz historię wypożyczeń dla danego użytkownika
        return (List<LoanEntity>) loanRepository.findByUserId(userId);
    }
    public void delete(Integer id) {
        loanRepository.deleteById(id);
    }
    public List<LoanEntity> getAllLoans() {
        return loanRepository.findAll();
    }
}
