package com.banking_transaction.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_transfers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Source account is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Source account must follow the pattern XXXXXXXXXX (10 digits)")
    @Column(name = "source_account", nullable = false, length = 10)
    private String sourceAccount;

    @NotBlank(message = "Destination account is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Destination account must follow the pattern XXXXXXXXXX (10 digits)")
    @Column(name = "destination_account", nullable = false, length = 10)
    private String destinationAccount;

    @NotNull(message = "Transfer amount is mandatory")
    @DecimalMin(value = "0.01", message = "Transfer amount must be greater than zero")
    @Column(name = "transfer_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal transferAmount;

    @NotNull(message = "Fee amount is mandatory")
    @Column(name = "fee_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal feeAmount;

    @NotNull(message = "Transfer date is mandatory")
    @Column(name = "transfer_date", nullable = false)
    private LocalDate transferDate;

    @NotNull(message = "Schedule date is mandatory")
    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

}
