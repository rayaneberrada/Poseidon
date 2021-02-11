package com.nnk.springboot.domain;

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

  Integer curveId;
  Timestamp asOfDate;
  Double term;
  Double value;
  Timestamp creationDate;
}
