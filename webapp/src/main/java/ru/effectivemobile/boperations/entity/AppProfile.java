package ru.effectivemobile.boperations.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.effectivemobile.boperations.domain.core.model.DomainProfile;
import ru.effectivemobile.boperations.domain.core.model.DomainUser;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "profiles")
public class AppProfile implements DomainProfile {

    /**
     * Идентификатор профиля
     * Идентификатор профиля равен идентификатору пользователя AppUser
     */
    @Id
    private UUID id;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Embedded
    private EmbeddedUser user;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "profile", orphanRemoval = true)
    private AppProfileName name;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "profile", orphanRemoval = true)
    private AppProfileBirthday birthday;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<AppProfileEmail> emails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<AppProfilePhone> phones;

    public AppProfile(UUID id) {
        this.id = id;
        this.user = new EmbeddedUser(id);
    }

    public void setName(String name) {
        this.name = new AppProfileName(name, this);
    }

    public void setBirthday(Instant birthday) {
        this.birthday = new AppProfileBirthday(birthday, this);
    }

    public void addEmail(String email) {
        if (emails == null) {
            emails = new HashSet<>();
        }
        emails.add(new AppProfileEmail(email, this));
    }

    public void addPhone(String phone) {
        if (phones == null) {
            phones = new HashSet<>();
        }
        phones.add(new AppProfilePhone(phone, this));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Embeddable
    public static class EmbeddedUser implements DomainUser {
        @Column(name = "id", nullable = false, insertable = false, updatable = false)
        private UUID id;
    }
}
