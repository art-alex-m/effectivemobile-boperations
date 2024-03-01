package ru.effectivemobile.boperations.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "account_balance")
public class AppAccountBalance {

    @Id
    private UUID accountId;

    @Column(name = "amount_topup", insertable = false, updatable = false)
    private BigDecimal amountTopup = BigDecimal.ZERO;

    @Column(name = "amount_withdraw", insertable = false, updatable = false)
    private BigDecimal amountWithdraw = BigDecimal.ZERO;

    @Column(name = "balance", insertable = false, updatable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToOne
    @PrimaryKeyJoinColumn
    private AppAccount account;
}
