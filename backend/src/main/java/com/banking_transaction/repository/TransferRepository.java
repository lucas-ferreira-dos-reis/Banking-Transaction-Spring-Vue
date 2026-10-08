package com.banking_transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking_transaction.model.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
