package com.tecacet.money.domain;

import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.CreationTimestamp;

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
@Builder
//The following are required by the JPA contract
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Client ID is required")
    private String clientId;

    private String description;

    @Columns(columns = {@Column(name = "amount"), @Column(name = "currency")})
    @NotNull(message = "Amount is required")
    private MonetaryAmount amount;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

}
