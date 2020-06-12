package com.tecacet.money.controller;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceDto {

    private String clientId;
    private String currency;
    private BigDecimal amount;
    private LocalDate invoiceDate;
    private LocalDate dueDate;

}
