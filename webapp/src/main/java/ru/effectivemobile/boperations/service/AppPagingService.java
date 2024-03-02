package ru.effectivemobile.boperations.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import ru.effectivemobile.boperations.domain.core.boundary.request.Paging;

import java.util.Collection;
import java.util.Optional;

/**
 * Создание объекта навигации для Spring Jpa Repository
 */
@Service
public class AppPagingService {

    /**
     * Создает объект навигации
     *
     * @param paging Paging
     * @return Pageable
     */
    public Pageable paginate(Paging paging) {

        int page = Optional.ofNullable(paging).map(Paging::getPage).orElse(Paging.DEFAULT_PAGE);
        int limit = Optional.ofNullable(paging).map(Paging::getLimit).orElse(Paging.DEFAULT_LIMIT);

        return Optional.ofNullable(paging)
                .map(Paging::getSorting)
                .filter(set -> !set.isEmpty())
                .map(Collection::stream)
                .map(sortings -> sortings
                        .map(sorting -> new Order(
                                sorting.getAsc() > 0 ? Direction.ASC : Direction.DESC,
                                sorting.getField()
                        ))
                        .toList())
                .map(Sort::by)
                .map(sort -> PageRequest.of(page, limit, sort))
                .orElseGet(() -> PageRequest.of(page, limit));
    }
}
