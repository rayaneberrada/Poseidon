package com.nnk.springboot.controllers.validators;

import com.nnk.springboot.controllers.constraint.NumericFieldConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumericalFieldValidator
    implements ConstraintValidator<NumericFieldConstraint, String> {
  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return s != null && s.matches("^[0-9]+$");
  }
}
