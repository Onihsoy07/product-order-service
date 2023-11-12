package com.example.productorderservice.domain.dto;

import com.example.productorderservice.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;
    private String itemName;
    private int price;
    private String discountPolicy;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.discountPolicy = item.getDiscountPolicy().getValue();
    }

}
