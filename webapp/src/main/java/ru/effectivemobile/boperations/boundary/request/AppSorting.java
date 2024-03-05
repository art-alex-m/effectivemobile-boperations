package ru.effectivemobile.boperations.boundary.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.effectivemobile.boperations.domain.core.boundary.request.Sorting;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppSorting implements Sorting {

    /**
     * Поле сортировки
     */
    @NotEmpty
    private String field;

    /**
     * Направление сортировки
     */
    private int asc = ASC;

    public static AppSorting from(String property) {
        int asc = property.charAt(0) == '-' ? DESC : ASC;
        String field = asc < 0 ? property.substring(1) : property;
        return new AppSorting(field, asc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppSorting that)) return false;
        return Objects.equals(getField(), that.getField());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getField());
    }
}
