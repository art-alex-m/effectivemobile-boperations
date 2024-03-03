package ru.effectivemobile.boperations.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.boperations.boundary.AppProfileSearchInteractor;
import ru.effectivemobile.boperations.boundary.request.AppProfileSearchRequest;
import ru.effectivemobile.boperations.dto.AppProfileDto;
import ru.effectivemobile.boperations.mapper.DomainProfileToDtoMapper;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppProfileController {

    private final AppProfileSearchInteractor interactor;

    private final DomainProfileToDtoMapper mapper;

    @GetMapping
    public Page<AppProfileDto> search(@Valid AppProfileSearchRequest request) {
        return interactor.search(request).map(mapper::toProfileDto);
    }
}
