package com.nnk.springboot.controllers.validators;

import com.nnk.springboot.controllers.constraint.PasswordFieldConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Class to verify a password contain at least one upper character, 8 entries, one number and one
 * symbol
 */
public class PasswordFieldValidator
    implements ConstraintValidator<PasswordFieldConstraint, String> {

  @Override
  public void initialize(PasswordFieldConstraint constraintAnnotation) {}

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return s != null
        && s.matches(".*[A-Z]+.*")
        && (s.length() >= 8)
        && s.matches(".*[0-9]+.*")
        && s.matches(".*\\W.*");
  }
}
