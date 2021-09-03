package com.tecacet.money.domain;

import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invoice")
@Getter
@Builder
//The following are required by the JPA contract
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Client ID is required")
    private String clientId;

    @Columns(columns = {@Column(name = "amount"), @Column(name = "currency")})
    @NotNull(message = "Total is required")
    private MonetaryAmount total;

    @NotNull(message = "discountPercent is required")
    @Builder.Default
    private BigDecimal discountPercent = BigDecimal.ZERO;

    @NotNull(message = "Invoice date is required")
    private LocalDate invoiceDate;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

}
