package com.nnk.springboot.domain;

import com.nnk.springboot.controllers.constraint.NumericFieldConstraint;
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
@Table(name = "trade")
public class Trade {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer tradeId;

  String account;
  String type;
  @NumericFieldConstraint Double buyQuantity;
  @NumericFieldConstraint Double sellQuantity;
  @NumericFieldConstraint Double buyPrice;
  @NumericFieldConstraint Double sellPrice;
  String benchmark;
  Timestamp tradeDate;
  String security;
  String status;
  String trader;
  String book;
  String creationName;
  Timestamp creationDate;
  String revisionName;
  Timestamp revisionDate;
  String dealName;
  String dealType;
  String sourceListId;
  String side;
}
