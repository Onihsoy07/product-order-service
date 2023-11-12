package com.example.productorderservice.service;

import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.domain.dto.OrdersSaveDto;
import com.example.productorderservice.domain.dto.OrdersDto;
import com.example.productorderservice.domain.enumeration.DiscountPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

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
        ordersService.saveOrders(new OrdersSaveDto(itemId, quantity));

        //then


    }

    @Test
    void 주푼조회() {
        //given
        final Long itemId = 1L;
        final Long ordersId = 1L;
        final int quantity = 1004;
        addItem();
        addOrders(itemId);

        //when
        OrdersDto ordersDto = ordersService.getOrders(ordersId);

        //then
        assertThat(ordersDto.getId()).isEqualTo(ordersId);
        assertThat(ordersDto.getQuantity()).isEqualTo(quantity);
        assertThat(ordersDto.getItemDto().getId()).isEqualTo(itemId);
        assertThat(ordersDto.getItemDto().getItemName()).isEqualTo("상품명");
        assertThat(ordersDto.getItemDto().getPrice()).isEqualTo(9999);
        assertThat(ordersDto.getItemDto().getDiscountPolicy()).isEqualTo(DiscountPolicy.NONE.getValue());


    }

    private void addItem() {
        ItemSaveDto itemSaveDto =  new ItemSaveDto("상품명", 9999, DiscountPolicy.NONE.getValue());
        itemService.addItem(itemSaveDto);
    }

    private void addOrders(Long itemId) {
        OrdersSaveDto ordersSaveDto = new OrdersSaveDto(itemId, 1004);
        ordersService.saveOrders(ordersSaveDto);
    }
}
