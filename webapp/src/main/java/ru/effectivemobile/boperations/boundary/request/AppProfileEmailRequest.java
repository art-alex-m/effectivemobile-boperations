package ru.effectivemobile.boperations.boundary.request;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.effectivemobile.boperations.constraint.BusinessLogicCheckGroup;
import ru.effectivemobile.boperations.constraint.EmailNotTaken;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@GroupSequence(value = {AppProfileEmailRequest.class, BusinessLogicCheckGroup.class})
public class AppProfileEmailRequest {
    @NotEmpty
    @Email
    @EmailNotTaken(groups = BusinessLogicCheckGroup.class)
    private String value;
}
