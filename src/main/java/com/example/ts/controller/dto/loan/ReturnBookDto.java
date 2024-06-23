package com.example.ts.controller.dto.loan;

public class ReturnBookDto {
    private Integer loanId;

    public ReturnBookDto(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }
}
