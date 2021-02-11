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
@Table(name = "trade")
public class Trade {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer tradeId;

  String account;
  String type;
  Double buyQuantity;
  Double sellQuantity;
  Double buyPrice;
  Double sellPrice;
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
