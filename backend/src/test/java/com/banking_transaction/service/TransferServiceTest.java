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

import com.banking_transaction.domain.dto.TransferDto;
import com.banking_transaction.domain.model.Transfer;
import com.banking_transaction.exception.BusinessException;
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
        LocalDate today = LocalDate.now();
        TransferDto dto = TransferDto.builder()
                .sourceAccount("1234567890")
                .destinationAccount("0987654321")
                .transferAmount(new BigDecimal("100.00"))
                .scheduleDate(today)
                .build();

        when(transferRepository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transfer result = transferService.scheduleTransfer(dto);

        assertEquals(new BigDecimal("5.50"), result.getFeeAmount());
    }

    @Test
    void shouldCalculateFeeFor1To10DaysTransfer() {
        LocalDate today = LocalDate.now();
        LocalDate transferDate = today.plusDays(5);
        TransferDto dto = TransferDto.builder()
                .sourceAccount("1234567890")
                .destinationAccount("0987654321")
                .transferAmount(new BigDecimal("100.00"))
                .scheduleDate(transferDate)
                .build();

        when(transferRepository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transfer result = transferService.scheduleTransfer(dto);

        assertEquals(new BigDecimal("12.00"), result.getFeeAmount());
    }

    @Test
    void shouldThrowExceptionWhenNoTaxIsApplicable() {
        LocalDate today = LocalDate.now();
        LocalDate transferDate = today.plusDays(51);
        TransferDto dto = TransferDto.builder()
                .sourceAccount("1234567890")
                .destinationAccount("0987654321")
                .transferAmount(new BigDecimal("100.00"))
                .scheduleDate(transferDate)
                .build();

        Exception exception = assertThrows(BusinessException.class, () -> {
            transferService.scheduleTransfer(dto);
        });

        assertTrue(exception.getMessage().contains("No applicable tax found"));
    }

}
