package com.example.ts.controller;

import com.example.ts.infrastructure.entity.LoanEntity;
import com.example.ts.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<?> addLoan(@RequestBody LoanEntity loan){
        try {
            LoanEntity addedLoan = loanService.addLoan(loan);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedLoan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add loan: " + e.getMessage());
        }
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<?> getOne(@PathVariable Integer id){
        try {
            LoanEntity loan = loanService.getOne(id);
            if (loan != null) {
                return ResponseEntity.ok(loan);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve loan: " + e.getMessage());
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable Integer id){
        try {
            loanService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete loan: " + e.getMessage());
        }
    }

    @Secured("ROLE_READER")
    @PostMapping("/borrow")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> borrowBook(@RequestParam Integer userId, @RequestParam Integer bookId) {
        try {
            LoanEntity loan = loanService.borrowBook(userId, bookId);
            return ResponseEntity.ok(loan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to borrow book: " + e.getMessage());
        }
    }

    @Secured("ROLE_READER")
    @DeleteMapping("/return/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Integer id) {
        try {
            loanService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to return book: " + e.getMessage());
        }
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<?> getAllLoans() {
        try {
            List<LoanEntity> loans = loanService.getAllLoans();
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve loans: " + e.getMessage());
        }
    }

    @Secured("ROLE_READER")
    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getLoanHistory(@PathVariable Integer userId) {
        try {
            List<LoanEntity> loanHistory = loanService.getLoanHistory(userId);
            return ResponseEntity.ok(loanHistory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve loan history: " + e.getMessage());
        }
    }
}

