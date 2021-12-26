package com.store.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "decorations")
public class Decoration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long decorationId;

    @Column(name = "decorationName")
    private String decorationName;

    @Column(name = "price")
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private DecorationCategory category;

    @Column(name = "description")
    private String description;
}
