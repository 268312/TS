package com.example.ts.controller;

import com.example.ts.infrastructure.entity.LoanEntity;
import com.example.ts.infrastructure.entity.UserEntity;
import com.example.ts.service.LoanService;
import com.example.ts.service.UserService;
import com.example.ts.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final LoanService loanService;

    @Autowired
    public UserController(UserService userService, LoanService loanService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.loanService = loanService;
        this.passwordEncoder = passwordEncoder;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<?> addUser(@RequestBody UserEntity user) {
        try {
            if (userService.existsByUsername(user.getName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
            }
            UserEntity addedUser = userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user: " + e.getMessage());
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<?> deleteUser(@PathVariable Integer id){
        try {
            userService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user: " + e.getMessage());
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<?> getUser(@PathVariable Integer id){
        try {
            UserEntity user = userService.get(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Faild to get user: " + e.getMessage());
        }

    }

    @Secured("ROLE_READER")
    @PostMapping("/borrow/{userId}/{bookId}")
    public ResponseEntity<?> borrowBook(@PathVariable Integer userId, @PathVariable Integer bookId) {
        // Wywołaj serwis, aby pożyczyć książkę
        LoanEntity loan = loanService.borrowBook(userId, bookId);
        if (loan != null) {
            return ResponseEntity.ok(loan);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book not available for borrowing");
        }
    }

    @Secured("ROLE_READER")
    @PostMapping("/return/{loanId}")
    public ResponseEntity<?> returnBook(@PathVariable Integer loanId) {
        // Wywołaj serwis, aby zwrócić książkę
        loanService.returnBook(loanId);
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_READER")
    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getLoanHistory(@PathVariable Integer userId) {
        // Wywołaj serwis, aby pobrać historię wypożyczeń
        List<LoanEntity> loanHistory = loanService.getLoanHistory(userId);
        return ResponseEntity.ok(loanHistory);
    }
}



