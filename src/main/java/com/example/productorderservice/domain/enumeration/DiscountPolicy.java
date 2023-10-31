package com.example.productorderservice.domain.enumeration;

import lombok.Getter;

@Getter
public enum DiscountPolicy {

    NONE("none");

    private final String value;

    DiscountPolicy(String value) {
        this.value = value;
    }
}
