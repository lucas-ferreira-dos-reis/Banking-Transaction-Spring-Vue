package com.banking_transaction.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banking_transaction.exception.BusinessException;
import com.banking_transaction.model.Transfer;
import com.banking_transaction.repository.TransferRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;

    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    public Transfer scheduleTransfer(Transfer transfer) {
        Long daysDiff = ChronoUnit.DAYS.between(transfer.getScheduleDate(), transfer.getTransferDate());

        BigDecimal fee = calculateFee(transfer.getTransferAmount(), daysDiff);
        transfer.setFeeAmount(fee);

        return transferRepository.save(transfer);
    }

    private BigDecimal calculateFee(BigDecimal amount, Long days) {
        if (days == 0) {
            BigDecimal percentageFee = amount.multiply(new BigDecimal("0.025"));
            return new BigDecimal("3.00").add(percentageFee).setScale(2, RoundingMode.HALF_UP);
        } else if (days >= 1 && days <= 10) {
            return new BigDecimal("12.00").setScale(2, RoundingMode.HALF_UP);
        } else if (days >= 11 && days <= 20) {
            return amount.multiply(new BigDecimal("0.082")).setScale(2, RoundingMode.HALF_UP);
        } else if (days >= 21 && days <= 30) {
            return amount.multiply(new BigDecimal("0.069")).setScale(2, RoundingMode.HALF_UP);
        } else if (days >= 31 && days <= 40) {
            return amount.multiply(new BigDecimal("0.047")).setScale(2, RoundingMode.HALF_UP);
        } else if (days >= 41 && days <= 50) {
            return amount.multiply(new BigDecimal("0.017")).setScale(2, RoundingMode.HALF_UP);
        } else {
            throw new BusinessException(
                    "No applicable tax found for the selected transfer date. Transfer not allowed.");
        }
    }

}
