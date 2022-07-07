package com.nttdata.apirestcustomers.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class TypeCustomerValidator implements ConstraintValidator<TypeCustomerValidation, String> {

    List<String> typesCustomers = Arrays.asList("B", "P");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return typesCustomers.contains(value);
    }
}
