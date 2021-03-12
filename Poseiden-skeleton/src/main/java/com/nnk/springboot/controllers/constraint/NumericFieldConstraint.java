package com.nnk.springboot.controllers.constraint;

import com.nnk.springboot.controllers.validators.NumericalFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NumericalFieldValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumericFieldConstraint {
  String message() default "Field should only comport numbers";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
