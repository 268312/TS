package com.example.ts.controller;

import com.example.ts.controller.dto.loan.AddLoanDto;
import com.example.ts.infrastructure.entity.LoanEntity;
import com.example.ts.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling operations related to loans in library
 */

@RestController
@RequestMapping("/api/loan")
@CrossOrigin(origins = "http://localhost:3000")
public class LoanController {
    private final LoanService loanService;

    /**
     * Constructor for LoanController
     * @param loanService The service for handling operations related to loans
     */
    @Autowired
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    /**
     * Adding a new loan
     * @param loan The loan entity to add
     * @return ResponseEntity containing the added loan entity
     */
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


    /**
     * Retrieving a loan
     * @param id The id of a loan to be retrieved
     * @return ResponseEntity containing the loan with given id
     */
//    @Secured("ROLE_ADMIN")
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

    /**
     * Deleting a loan
     * @param id The id of the loan to be deleted
     * @return ResponseEntity indicating the result of the deletion
     */
//    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable Integer id){
        try {
            loanService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete loan: " + e.getMessage());
        }
    }

    /**
     * Borrowing a book

     * @return ResponseEntity containing the loan that was created
     */
//    @Secured("ROLE_READER")
    @PostMapping("/borrow")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> borrowBook(@RequestBody AddLoanDto addLoanDto) {
        try {

            loanService.borrowBook(addLoanDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to borrow book: " + e.getMessage());
        }
    }

    /**
     * Returning a book
     * @param id id of the returned book
     * @return ResponseEntity indicating the result of the return
     */
//    @Secured("ROLE_READER")
    @DeleteMapping("/return/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Integer id) {
        try {
            loanService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to return book: " + e.getMessage());
        }
    }


    /**
     * Getting all loans
     * @return ResponseEntity containing a list with all the loans
     */
//    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<?> getAllLoans() {
        try {
            List<LoanEntity> loans = loanService.getAllLoans();
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve loans: " + e.getMessage());
        }
    }


    /**
     * Getting loan history for a particular user
     * @param userId the id of the user
     * @return ResponseEntity containing the list of loans made by the user
     */
//    @Secured("ROLE_READER")
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

