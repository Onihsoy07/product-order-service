package com.example.productorderservice.domain.dto;

import com.example.productorderservice.domain.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {

    private Long id;
    private int quantity;
    private ItemDto itemDto;

    public OrdersDto(Orders orders) {
        this.id = orders.getId();
        this.quantity = orders.getQuantity();
        this.itemDto = new ItemDto(orders.getItem());
    }
}
