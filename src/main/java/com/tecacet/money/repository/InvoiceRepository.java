package com.tecacet.money.repository;

import com.tecacet.money.domain.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvoiceRepository  extends JpaRepository<Invoice, UUID> {
}
