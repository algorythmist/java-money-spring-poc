package com.tecacet.money.domain;

import javax.money.MonetaryAmount;
import java.time.LocalDate;

public interface InvoiceBuilder {

    InvoiceBuilder clientId(String clientId);

    InvoiceBuilder total(MonetaryAmount total);

    InvoiceBuilder invoiceDate(LocalDate date);

    InvoiceBuilder dueDate(LocalDate dueDate);

    Invoice build();
}
