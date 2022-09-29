package com.shdwraze.dmarket.entity;

import com.shdwraze.dmarket.entity.enums.Type;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "heroes_id")
    private Hero hero;
}
