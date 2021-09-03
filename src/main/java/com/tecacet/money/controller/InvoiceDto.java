package com.tecacet.money.controller;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class InvoiceDto {

    private String clientId;
    private String currency;
    private BigDecimal amount;
    private LocalDate invoiceDate;
    private LocalDate dueDate;

}
