package ru.effectivemobile.boperations.boundary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.effectivemobile.boperations.boundary.response.AppCreateUserResponse;
import ru.effectivemobile.boperations.domain.core.boundary.CreateUserInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.request.CreateUserRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.CreateUserResponse;
import ru.effectivemobile.boperations.entity.AppAccount;
import ru.effectivemobile.boperations.entity.AppAccountOperation;
import ru.effectivemobile.boperations.entity.AppProfile;
import ru.effectivemobile.boperations.entity.AppUser;
import ru.effectivemobile.boperations.repository.AppAccountJpaRepository;
import ru.effectivemobile.boperations.repository.AppAccountOperationJpaRepository;
import ru.effectivemobile.boperations.repository.AppProfileJpaRepository;
import ru.effectivemobile.boperations.repository.AppUserJpaRepository;

import static ru.effectivemobile.boperations.domain.core.model.AccountOperationType.TOPUP;

@Component
@AllArgsConstructor
@Builder
public class AppCreateUserInteractor implements CreateUserInteractor {

    private final PasswordEncoder passwordEncoder;

    private final AppUserJpaRepository userDbRepository;

    private final AppProfileJpaRepository profileDbRepository;

    private final AppAccountJpaRepository bankAccountDbRepository;

    private final AppAccountOperationJpaRepository operationDbRepository;

    @Transactional
    @Override
    public CreateUserResponse create(CreateUserRequest request) {
        AppUser user = new AppUser(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        userDbRepository.save(user);

        AppProfile profile = new AppProfile(user.getId());
        profile.setBirthday(request.getBirthday());
        profile.setName(request.getName());
        profile.addEmail(request.getEmail());
        profile.addPhone(request.getPhone());
        profileDbRepository.save(profile);

        AppAccount account = new AppAccount(user);
        bankAccountDbRepository.save(account);

        AppAccountOperation operation = new AppAccountOperation(account, request.getStartBalance(), TOPUP);
        operationDbRepository.save(operation);

        return new AppCreateUserResponse(user);
    }
}
