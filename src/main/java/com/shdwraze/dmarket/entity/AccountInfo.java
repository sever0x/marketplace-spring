package com.shdwraze.dmarket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "accounts_info")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {
    @Id
    @Column(name = "accounts_id")
    private int accountID;

    private String email;

    private String phone;

    @Column(name = "steam_id")
    private String steamID;

    private double balance;

    @Column(name = "registration_date")
    private Date registrationDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "accounts_id")
    @JsonBackReference
    private Account account;
}
