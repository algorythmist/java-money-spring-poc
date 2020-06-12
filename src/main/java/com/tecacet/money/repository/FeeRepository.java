package com.tecacet.money.repository;

import com.tecacet.money.domain.Fee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeeRepository extends JpaRepository<Fee, UUID> {

    List<Fee> findByClientId(String clientId);
}
