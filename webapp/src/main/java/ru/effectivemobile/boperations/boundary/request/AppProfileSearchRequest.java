package ru.effectivemobile.boperations.boundary.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.effectivemobile.boperations.constraint.Phone;
import ru.effectivemobile.boperations.constraint.SortingFields;
import ru.effectivemobile.boperations.domain.core.boundary.request.ProfileSearchRequest;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppProfileSearchRequest implements ProfileSearchRequest {

    private String name;

    @Phone
    private String phone;

    @Schema(format = "email")
    @Email
    private String email;

    private Instant birthday;

    @ArraySchema(schema = @Schema(allowableValues = {"name_value", "createdAt", "birthday_value", "-name_value",
            "-createdAt", "-birthday_value"}))
    @SortingFields(expected = {"name_value", "createdAt", "birthday_value"})
    private List<String> sort;

    @Positive
    @Schema(defaultValue = "50", minimum = "1")
    private int limit = DEFAULT_LIMIT;

    @PositiveOrZero
    @Max(500)
    @Schema(minimum = "0", maximum = "500", defaultValue = "0")
    private int page = DEFAULT_PAGE;

    @Schema(hidden = true)
    public Set<AppSorting> getSorting() {
        return Optional.ofNullable(sort)
                .filter(list -> !list.isEmpty())
                .map(Collection::stream)
                .map(stream -> stream.map(AppSorting::from))
                .map(Stream::toList)
                .map(Set::copyOf)
                .orElse(Collections.emptySet());
    }
}
