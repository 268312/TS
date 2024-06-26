package com.example.ts.service;

import com.example.ts.controller.dto.loan.AddLoanDto;
import com.example.ts.controller.dto.loan.ReturnBookDto;
import com.example.ts.infrastructure.entity.BookEntity;
import com.example.ts.infrastructure.entity.LoanEntity;
import com.example.ts.infrastructure.entity.UserEntity;
import com.example.ts.infrastructure.repository.BookRepository;
import com.example.ts.infrastructure.repository.LoanRepository;
import com.example.ts.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.Calendar;
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

    public LoanEntity borrowBook(AddLoanDto addLoanDto) {
        Integer userId = addLoanDto.getUserId();
        Integer bookId = addLoanDto.getBookId();
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        LoanEntity loan = new LoanEntity();
        loan.setUser(user);
        loan.setLoanDate(new Date(System.currentTimeMillis()));
        BookEntity book = bookRepository.findById(bookId).orElse(null);
        // Ustawienie książki (bookId przekazywane z żądania)
        loan.setBook(book);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(System.currentTimeMillis()));
        calendar.add(Calendar.WEEK_OF_YEAR, 3); // Dodaje 3 tygodnie do daty wypożyczenia
        Date dueDate = new Date(calendar.getTimeInMillis());
        loan.setDueDate(dueDate);
        return loanRepository.save(loan);
    }
    @Transactional
    public LoanEntity returnBook(ReturnBookDto returnBookDto) {
        // Pobierz wypożyczenie
        Integer loanId = returnBookDto.getLoanId();
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        loan.setReturnDate(new java.sql.Date(System.currentTimeMillis()));

        loanRepository.save(loan);

        // Zapisz zmiany
        return loanRepository.save(loan);
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
