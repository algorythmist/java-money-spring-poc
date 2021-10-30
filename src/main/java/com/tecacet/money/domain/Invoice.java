package com.tecacet.money.domain;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.CreationTimestamp;

import javax.money.MonetaryAmount;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invoice")
public class Invoice {

    private Invoice() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Client ID is required")
    private String clientId;

    @Columns(columns = {@Column(name = "amount"), @Column(name = "currency")})
    @NotNull(message = "Total is required")
    private MonetaryAmount total;

    @NotNull(message = "discountPercent is required")
    private final BigDecimal discountPercent = BigDecimal.ZERO;

    @NotNull(message = "Invoice date is required")
    private LocalDate invoiceDate;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    public UUID getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public MonetaryAmount getTotal() {
        return total;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    private static class InvoiceBuilderPrv implements InvoiceBuilder {

        private final Invoice invoice = new Invoice();

        @Override
        public InvoiceBuilder clientId(String clientId) {
            invoice.clientId = clientId;
            return this;
        }

        @Override
        public InvoiceBuilder total(MonetaryAmount total) {
            invoice.total = total;
            return this;
        }

        @Override
        public InvoiceBuilder invoiceDate(LocalDate date) {
            invoice.invoiceDate = date;
            return this;
        }

        @Override
        public InvoiceBuilder dueDate(LocalDate dueDate) {
            invoice.dueDate = dueDate;
            return this;
        }

        @Override
        public Invoice build() {
            return invoice;
        }
    }

    public static InvoiceBuilder builder() {
        return new InvoiceBuilderPrv();
    }

}
