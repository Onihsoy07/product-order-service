package com.example.productorderservice.service;

import com.example.productorderservice.domain.dto.OrdersSaveDto;
import com.example.productorderservice.domain.dto.OrdersDto;
import com.example.productorderservice.domain.entity.Item;
import com.example.productorderservice.domain.entity.Orders;
import com.example.productorderservice.repository.ItemRepository;
import com.example.productorderservice.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ItemRepository itemRepository;

    public void saveOrders(OrdersSaveDto ordersSaveDto) {
        Orders orders = Orders.builder()
                .item(getItemEntity(ordersSaveDto.getItemId()))
                .quantity(ordersSaveDto.getQuantity())
                .build();

        ordersRepository.save(orders);
    }

    public OrdersDto getOrders(Long ordersId) {
        Orders orders = getOrdersEntity(ordersId);
        return new OrdersDto(orders);
    }

    private Item getItemEntity(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        });
        return item;
    }

    private Orders getOrdersEntity(Long ordersId) {
        Orders orders = ordersRepository.findById(ordersId).orElseThrow(() -> {
            throw new IllegalArgumentException("주문이 존재하지 않습니다");
        });
        return orders;
    }
}
