package com.banking_transaction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.banking_transaction.exception.BusinessException;
import com.banking_transaction.model.Transfer;
import com.banking_transaction.repository.TransferRepository;

public class TransferServiceTest {

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCalculateFeeForSameDayTransfer() {
        LocalDate today = LocalDate.of(2026, 10, 8);
        Transfer transfer = new Transfer("1234567890", "0987654321", new BigDecimal("100.00"), null, today, today);

        when(transferRepository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transfer result = transferService.scheduleTransfer(transfer);

        assertEquals(new BigDecimal("5.50"), result.getFeeAmount());
    }

    @Test
    void shouldCalculateFeeFor1To10DaysTransfer() {
        LocalDate scheduleDate = LocalDate.of(2026, 10, 8);
        LocalDate transferDate = scheduleDate.plusDays(5);
        Transfer transfer = new Transfer("1234567890", "0987654321", new BigDecimal("100.00"), null,
                transferDate, scheduleDate);

        when(transferRepository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transfer result = transferService.scheduleTransfer(transfer);

        assertEquals(new BigDecimal("12.00"), result.getFeeAmount());
    }

    @Test
    void shouldThrowExceptionWhenNoTaxIsApplicable() {
        LocalDate scheduleDate = LocalDate.of(2026, 10, 8);
        LocalDate transferDate = scheduleDate.plusDays(51);
        Transfer transfer = new Transfer("1234567890", "0987654321", new BigDecimal("100.00"), null,
                transferDate, scheduleDate);

        Exception exception = assertThrows(BusinessException.class, () -> {
            transferService.scheduleTransfer(transfer);
        });

        assertTrue(exception.getMessage().contains("No applicable tax found"));
    }

}
