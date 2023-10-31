package com.example.productorderservice.domain;

import com.example.productorderservice.domain.enumeration.DiscountPolicy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(schema = "order_service")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String itemName;

    @Column(nullable = false, unique = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    private DiscountPolicy discountPolicy;

    @Builder
    public Item(String itemName, int price, DiscountPolicy discountPolicy) {
        this.itemName = itemName;
        this.price = price;
        this.discountPolicy = discountPolicy;
    }
}
