package com.banking_transaction.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking_transaction.domain.dto.TransferDto;
import com.banking_transaction.domain.model.Transfer;
import com.banking_transaction.exception.BusinessException;
import com.banking_transaction.service.TransferService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/transfers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping
    public ResponseEntity<List<Transfer>> getAllTransfers() {
        List<Transfer> transfers = transferService.getAllTransfers();
        return ResponseEntity.ok(transfers);
    }

    @PostMapping
    public ResponseEntity<Object> scheduleTransfer(@Valid @RequestBody TransferDto dto) {
        try {
            Transfer transfer = transferService.scheduleTransfer(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(transfer);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
