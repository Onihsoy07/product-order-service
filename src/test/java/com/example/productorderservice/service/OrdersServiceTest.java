package com.example.productorderservice.service;

import com.example.productorderservice.config.ApiTestConfig;
import com.example.productorderservice.domain.dto.ItemDto;
import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.domain.dto.OrdersSaveDto;
import com.example.productorderservice.domain.dto.OrdersDto;
import com.example.productorderservice.domain.enumeration.DiscountPolicy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class OrdersServiceTest extends ApiTestConfig {

    @Mock
    OrdersService ordersServiceMock;
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
    void 주문조회() {
        //given
        final Long itemId = 1L;
        final Long ordersId = 1L;
        final int quantity = 1004;
        Mockito.when(ordersServiceMock.getOrders(itemId))
                .thenReturn(new OrdersDto(ordersId, quantity, new ItemDto(itemId, "상품명", 9999, DiscountPolicy.NONE.getValue())));


        //when
        OrdersDto ordersDto = ordersServiceMock.getOrders(ordersId);

        //then
        assertThat(ordersDto.getId()).isEqualTo(ordersId);
        assertThat(ordersDto.getQuantity()).isEqualTo(quantity);
        assertThat(ordersDto.getItemDto().getId()).isEqualTo(itemId);
        assertThat(ordersDto.getItemDto().getItemName()).isEqualTo("상품명");
        assertThat(ordersDto.getItemDto().getPrice()).isEqualTo(9999);
        assertThat(ordersDto.getItemDto().getDiscountPolicy()).isEqualTo(DiscountPolicy.NONE.getValue());

    }

    @Test
    void 주문제거() {
        //given
        final Long itemId = 1L;
        final Long ordersId = 1L;
        addItem();
        addOrders(itemId);
        ItemDto item = itemService.getItem(itemId);
        OrdersDto orders = ordersService.getOrders(ordersId);

        //when
        ordersService.deleteOrders(ordersId);

        //then
        assertThatThrownBy(() -> ordersService.getOrders(ordersId)).isInstanceOf(IllegalArgumentException.class);
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
