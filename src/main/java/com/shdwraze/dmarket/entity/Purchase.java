package com.shdwraze.dmarket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shdwraze.dmarket.entity.enums.PurchaseStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "lots_id")
    private Lot lot;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    @JsonBackReference
    private Account buyer;

    private Date date;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;
}
