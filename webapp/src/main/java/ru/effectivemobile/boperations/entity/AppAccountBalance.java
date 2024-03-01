package ru.effectivemobile.boperations.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @Column(name = "amount_topup")
    private BigDecimal amountTopup = BigDecimal.ZERO;

    @Column(name = "amount_withdraw")
    private BigDecimal amountWithdraw = BigDecimal.ZERO;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private AppAccount account;

    public BigDecimal getBalance() {
        return amountTopup.subtract(amountWithdraw);
    }
}
