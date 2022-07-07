package com.nttdata.apirestcustomers.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TypeCustomerValidator.class)
public @interface TypeCustomerValidation {
    String message() default "This is not the valid typeCustomer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
