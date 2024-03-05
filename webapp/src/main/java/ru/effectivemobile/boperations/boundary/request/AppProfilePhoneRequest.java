package ru.effectivemobile.boperations.boundary.request;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.effectivemobile.boperations.constraint.BusinessLogicCheckGroup;
import ru.effectivemobile.boperations.constraint.Phone;
import ru.effectivemobile.boperations.constraint.PhoneNotTaken;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@GroupSequence(value = {AppProfilePhoneRequest.class, BusinessLogicCheckGroup.class})
public class AppProfilePhoneRequest {
    @NotEmpty
    @Phone
    @PhoneNotTaken(groups = BusinessLogicCheckGroup.class)
    private String value;
}
