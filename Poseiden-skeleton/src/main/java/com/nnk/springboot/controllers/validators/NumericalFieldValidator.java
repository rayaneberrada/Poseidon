package com.nnk.springboot.controllers.validators;

import com.nnk.springboot.controllers.constraint.NumericFieldConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** Class to verify that a field only contain numbers */
public class NumericalFieldValidator
    implements ConstraintValidator<NumericFieldConstraint, String> {
  @Override
  public void initialize(NumericFieldConstraint constraintAnnotation) {}

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return s != null && s.matches("^[0-9]+$");
  }
}
