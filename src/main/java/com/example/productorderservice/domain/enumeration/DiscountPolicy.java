package com.example.productorderservice.domain.enumeration;

import lombok.Getter;

@Getter
public enum DiscountPolicy {

    NONE("NONE");

    private final String value;

    DiscountPolicy(String value) {
        this.value = value;
    }
}
