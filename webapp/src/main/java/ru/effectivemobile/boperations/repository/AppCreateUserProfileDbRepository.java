package ru.effectivemobile.boperations.repository;

import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.domain.core.repository.CreateUserProfileDbRepository;
import ru.effectivemobile.boperations.domain.core.repository.request.CreateUserProfileDbRequest;

@Component
public class AppCreateUserProfileDbRepository implements CreateUserProfileDbRepository {
    @Override
    public boolean create(CreateUserProfileDbRequest request) {
        return false;
    }

    @Override
    public boolean existsPhone(String phone) {
        return false;
    }

    @Override
    public boolean existsEmail(String email) {
        return false;
    }
}
