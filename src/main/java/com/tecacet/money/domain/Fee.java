package com.tecacet.money.domain;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

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
@Table(name = "fee")
@Getter
@Setter
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String clientId;

    private String description;

    @Columns(columns = {@Column(name = "amount"), @Column(name = "currency")})
    @NotNull(message = "Amount is required")
    private MonetaryAmount amount;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

}
