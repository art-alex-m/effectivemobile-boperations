package ru.effectivemobile.boperations.boundary.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.boundary.response.ProfilePropertyCrudResponse;
import ru.effectivemobile.boperations.domain.core.model.ProfileProperty;

@AllArgsConstructor
@Getter
public class AppProfilePropertyCrudResponse<T> implements ProfilePropertyCrudResponse<T> {

    private final ProfileProperty<T> property;
}
