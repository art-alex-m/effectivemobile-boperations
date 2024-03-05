package ru.effectivemobile.boperations.domain.core.boundary.request;

import java.util.Set;

/**
 * Пагинация
 */
public interface Paging {
    int DEFAULT_PAGE = 0;

    int DEFAULT_LIMIT = 50;

    int getPage();

    int getLimit();

    Set<? extends Sorting> getSorting();
}
