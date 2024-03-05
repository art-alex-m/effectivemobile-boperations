package ru.effectivemobile.boperations.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component("pathParam")
@RequestScope
public class PathParam {

    private final Map<String, Object> pathVariables;

    public PathParam(HttpServletRequest httpServletRequest) {
        this.pathVariables = (Map<String, Object>) httpServletRequest
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }

    public Object get(String name) {
        return pathVariables.getOrDefault(name, "");
    }
}
