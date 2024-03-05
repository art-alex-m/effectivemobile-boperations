package ru.effectivemobile.boperations.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.effectivemobile.boperations.domain.core.model.Account;
import ru.effectivemobile.boperations.domain.core.model.DomainUser;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "accounts")
public class AppAccount implements Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    private EmbeddedUser user;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @OneToOne(mappedBy = "account")
    private AppAccountBalance accountBalance;

    @OneToOne(mappedBy = "account")
    private AppAccountFirstTopup firstTopup;

    public AppAccount(DomainUser user) {
        this.user = new EmbeddedUser(user.getId());
    }

    @Override
    public BigDecimal getBalance() {
        BigDecimal balance = Optional.ofNullable(accountBalance)
                .map(AppAccountBalance::getBalance)
                .orElse(BigDecimal.ZERO);

        return balance;
    }

    @Override
    public BigDecimal getFirstTopupAmount() {
        return Optional.ofNullable(firstTopup)
                .map(AppAccountFirstTopup::getAmount)
                .orElse(BigDecimal.ZERO);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Embeddable
    public static class EmbeddedUser implements DomainUser {
        @Column(name = "user_id", nullable = false)
        private UUID id;
    }
}
