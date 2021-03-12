package com.nnk.springboot.domain;

import com.nnk.springboot.controllers.constraint.NumericFieldConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "rating")
public class Rating {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer id;

  String moodysRating;
  String sandPRating;
  String fitchRating;
  @NumericFieldConstraint Integer orderNumber;
}
