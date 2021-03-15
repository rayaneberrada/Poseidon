package com.nnk.springboot.controllers.constraint;

import com.nnk.springboot.controllers.validators.PasswordFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordFieldValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordFieldConstraint {
  String message() default
      "Field should have at least one Upper case character, 8 characters and one number and a special character";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
