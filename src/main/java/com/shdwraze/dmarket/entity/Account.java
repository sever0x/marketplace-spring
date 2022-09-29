package com.shdwraze.dmarket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shdwraze.dmarket.entity.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private AccountInfo accountInfo;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH},
            mappedBy = "seller")
    @JsonManagedReference
    private List<Lot> lots;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH},
            mappedBy = "buyer")
    @JsonManagedReference
    private List<Purchase> purchases;
}
