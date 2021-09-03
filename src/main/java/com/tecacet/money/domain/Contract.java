package com.tecacet.money.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Entity
@Table(name = "contract")
@Getter
@Builder
//The following are required by the JPA contract
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Contract {

    public static final String DEFAULT_CURRENCY = "USD";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Client ID is required")
    private String clientId;

    @NotNull(message = "Invoice Currency is required")
    @Builder.Default
    private Currency invoiceCurrency = Currency.getInstance(DEFAULT_CURRENCY);

    @NotNull(message = "Discount Percent is required")
    @Builder.Default
    private BigDecimal discountPercent = BigDecimal.ZERO;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

}
