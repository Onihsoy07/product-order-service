package com.example.productorderservice.service;

import com.example.productorderservice.domain.dto.ItemDto;
import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.domain.dto.OrderSaveDto;
import com.example.productorderservice.domain.entity.Item;
import com.example.productorderservice.domain.entity.Orders;
import com.example.productorderservice.domain.enumeration.DiscountPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrdersServiceTest {

    @Autowired
    OrdersService ordersService;
    @Autowired
    ItemService itemService;

    @Test
    void 주문생성() {
        //given
        final Long itemId = 1L;
        final int quantity = 1004;
        addItem();

        //when
        ordersService.saveOrders(new OrderSaveDto(itemId, quantity));

        //then


    }

    private void addItem() {
        ItemSaveDto itemSaveDto =  new ItemSaveDto("상품명", 9999, DiscountPolicy.NONE.getValue());
        itemService.addItem(itemSaveDto);
    }
}
