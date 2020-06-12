@TypeDef(name = "MonetaryAmount",
        typeClass = MonetaryAmountUserType.class,
        defaultForType = MonetaryAmount.class)
package com.tecacet.money.domain;

import com.tecacet.money.repository.MonetaryAmountUserType;

import org.hibernate.annotations.TypeDef;

import javax.money.MonetaryAmount;
