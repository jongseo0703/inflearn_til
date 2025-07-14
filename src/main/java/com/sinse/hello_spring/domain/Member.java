package com.sinse.hello_spring.domain;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
}
