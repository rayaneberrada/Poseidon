package com.nnk.springboot.controllers.validators;

import com.nnk.springboot.controllers.constraint.PasswordFieldConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordFieldValidator
    implements ConstraintValidator<PasswordFieldConstraint, String> {

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return s != null
        && s.matches("(.*[A-Z].*)+  ")
        && (s.length() > 8)
        && s.matches("(.*[0-9].*)+ ")
        && s.matches("(.*[-_$#].*)+");
  }
}
