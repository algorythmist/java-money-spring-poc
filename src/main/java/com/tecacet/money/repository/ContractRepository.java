package com.tecacet.money.repository;

import com.tecacet.money.domain.Contract;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    Optional<Contract> findByClientId(String clientId);
}
