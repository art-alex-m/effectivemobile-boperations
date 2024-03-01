package ru.effectivemobile.boperations.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.effectivemobile.boperations.domain.core.model.AccountOperation;
import ru.effectivemobile.boperations.domain.core.model.AccountOperationType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "account_operations")
public class AppAccountOperation implements AccountOperation {

    @EmbeddedId
    private Pk primaryKey;

    @Column
    private BigDecimal amount;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private AccountOperationType type;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AppAccount account;

    public AppAccountOperation(AppAccount account, double amount, AccountOperationType type) {
        this(UUID.randomUUID(), account, amount, type);
    }

    public AppAccountOperation(UUID operationId, AppAccount account, double amount, AccountOperationType type) {
        this.primaryKey = new Pk(operationId, account.getId());
        this.account = account;
        this.amount = BigDecimal.valueOf(amount);
        this.type = type;
    }

    @Override
    public UUID getId() {
        return primaryKey.getOperationId();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "id")
        private UUID operationId;

        @Column(name = "account_id")
        private UUID accountId;
    }
}
