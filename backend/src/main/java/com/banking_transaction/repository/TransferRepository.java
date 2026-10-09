package com.banking_transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking_transaction.domain.model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
