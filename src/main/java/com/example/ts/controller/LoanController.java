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
    public @ResponseBody LoanEntity addLoan(@RequestBody LoanEntity loan){
        return loanService.addLoan(loan);
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/get/{id}")
    public @ResponseBody LoanEntity getOne(@PathVariable Integer id){
        return loanService.getOne(id);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody void delete(@PathVariable Integer id){
        loanService.delete(id);
    }

    @Secured("ROLE_READER")
    @PostMapping("/borrow")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LoanEntity> borrowBook(@RequestParam Integer userId, @RequestParam Integer bookId) {
        LoanEntity loan = loanService.borrowBook(userId, bookId);
        return ResponseEntity.ok(loan);
    }

    @Secured("ROLE_READER")
    @DeleteMapping("/return/{id}")
    public ResponseEntity<Void> returnBook(@PathVariable Integer id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<LoanEntity>> getAllLoans() {
        List<LoanEntity> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }
}
