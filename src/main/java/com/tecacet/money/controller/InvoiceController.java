package com.tecacet.money.controller;

import com.tecacet.money.domain.Invoice;
import com.tecacet.money.service.InvoiceService;
import com.tecacet.money.util.MoneyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping(value = "invoice/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvoiceDto> createInvoice(@PathVariable("clientId") String clientId) {
        Optional<Invoice> optional =
                invoiceService.createClientInvoice(clientId, LocalDate.now());
        return optional.map(entity -> ResponseEntity.ok(toDto(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    private InvoiceDto toDto(Invoice invoice) {
        return InvoiceDto.builder()
                .amount(MoneyUtil.extractAmount(invoice.getTotal()))
                .currency(MoneyUtil.extractCurrencyCode(invoice.getTotal()))
                .clientId(invoice.getClientId())
                .dueDate(invoice.getDueDate())
                .invoiceDate(invoice.getInvoiceDate())
                .build();
    }
}
