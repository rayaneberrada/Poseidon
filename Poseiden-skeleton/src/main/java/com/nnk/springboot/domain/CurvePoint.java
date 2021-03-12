package com.nnk.springboot.domain;

import com.nnk.springboot.controllers.constraint.NumericFieldConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "curvepoint")
public class CurvePoint {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer id;

  @NumericFieldConstraint Integer curveId;
  Timestamp asOfDate;
  @NumericFieldConstraint Double term;
  @NumericFieldConstraint Double value;
  Timestamp creationDate;
}
