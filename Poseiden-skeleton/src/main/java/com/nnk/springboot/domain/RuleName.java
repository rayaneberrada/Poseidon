package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "rulename")
public class RuleName {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer id;

  String name;
  String description;
  String json;
  String template;
  String sqlStr;
  String sqlPart;
}
