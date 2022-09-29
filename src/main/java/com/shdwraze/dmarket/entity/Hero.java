package com.shdwraze.dmarket.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "heroes")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
