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
@Table(name = "account_first_topup")
public class AppAccountFirstTopup {

    @Id
    @Column(name = "account_id")
    private UUID accountId;

    @Column(insertable = false, updatable = false)
    private BigDecimal amount;

    @OneToOne
    @PrimaryKeyJoinColumn
    private AppAccount account;
}
