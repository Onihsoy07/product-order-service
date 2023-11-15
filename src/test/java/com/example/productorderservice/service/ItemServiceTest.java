package com.example.productorderservice.service;

import com.example.productorderservice.config.ApiTestConfig;
import com.example.productorderservice.domain.dto.ItemDto;
import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.domain.dto.ItemUpdateDto;
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
public class ItemServiceTest extends ApiTestConfig {

    @Mock
    private ItemService itemServiceMock;
    @Autowired
    private ItemService itemService;

    @Test
    void 상품등록() {
        ItemSaveDto itemSaveDto = new ItemSaveDto("상품명", 9999, DiscountPolicy.NONE.getValue());

        itemService.addItem(itemSaveDto);
    }

    @Test
    void 상품조회() {
        //given
        String itemName = "상품명";
        int price = 9999;
        DiscountPolicy discountPolicy = DiscountPolicy.NONE;
        ItemSaveDto itemSaveDto = new ItemSaveDto(itemName, price, discountPolicy.getValue());
        itemService.addItem(itemSaveDto);
        final long itemId = 1L;

        Mockito.when(itemServiceMock.getItem(itemId)).thenReturn(new ItemDto(itemId, itemName, price, discountPolicy.getValue()));

        //when
        ItemDto itemDto = itemServiceMock.getItem(itemId);

        //then
        assertThat(itemDto).isNotNull();
        assertThat(itemDto.getItemName()).isEqualTo(itemName);
        assertThat(itemDto.getPrice()).isEqualTo(price);
        assertThat(itemDto.getDiscountPolicy()).isEqualTo(discountPolicy.getValue());
    }

    @Test
    void 상품수정() {
        //given
        String updateItemName = "상품명1";
        int updatePrice = 1004;
        DiscountPolicy updateDiscountPolicy = DiscountPolicy.NONE;
        final long itemId = 1L;
        ItemUpdateDto itemUpdateDto = new ItemUpdateDto(updateItemName, updatePrice, updateDiscountPolicy.getValue());

        Mockito.when(itemServiceMock.getItem(itemId)).thenReturn(new ItemDto(itemId, updateItemName, updatePrice, updateDiscountPolicy.getValue()));

        //when
        itemServiceMock.updateItem(itemId, itemUpdateDto);
        ItemDto item = itemServiceMock.getItem(itemId);

        //then
        assertThat(item).isNotNull();
        assertThat(item.getItemName()).isEqualTo(updateItemName);
        assertThat(item.getPrice()).isEqualTo(updatePrice);
        assertThat(item.getDiscountPolicy()).isEqualTo(updateDiscountPolicy.getValue());

    }

    @Test
    void 상품제거() {
        //given
        상품등록();
        final Long itemId = 1L;

        //when
        itemService.deleteItem(itemId);

        //then
        assertThatThrownBy(() -> itemService.getItem(itemId)).isInstanceOf(IllegalArgumentException.class);

    }
}
