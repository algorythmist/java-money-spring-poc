package com.tecacet.money.domain;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.money.CurrencyUnit;
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
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String clientId;

    @NotNull
    private String targetCurrency;

    @Columns(columns = {
            @Column(name = "amount"),
            @Column(name = "currency")})
    @NotNull(message = "Total is required")
    private MonetaryAmount total;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

}
