package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "bidlist")
public class BidList {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer BidListId;

  String account;
  String type;
  Double bidQuantity;
  Double askQuantity;
  Double bid;
  Double ask;
  String benchmark;
  Timestamp bidListDate;
  String commentary;
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
