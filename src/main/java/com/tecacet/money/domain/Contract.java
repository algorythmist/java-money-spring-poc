package com.tecacet.money.domain;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "contract")
@Getter
@Setter
public class Contract {

    public static final String DEFAULT_CURRENCY = "USD";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String clientId;

    @NotNull
    private Currency invoiceCurrency = Currency.getInstance(DEFAULT_CURRENCY);

    @NotNull
    private BigDecimal discountPercent = BigDecimal.ZERO;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

}
