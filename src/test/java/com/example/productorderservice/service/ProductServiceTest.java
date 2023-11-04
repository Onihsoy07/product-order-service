package com.example.productorderservice.service;

import com.example.productorderservice.domain.dto.ItemDto;
import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.domain.enumeration.DiscountPolicy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void 상품등록() {
        ItemSaveDto itemSaveDto = new ItemSaveDto("상품명", 9999, DiscountPolicy.NONE.getValue());

        productService.addItem(itemSaveDto);
    }

    @Test
    void 상품조회() {
        //given
        String itemName = "상품명";
        int price = 9999;
        DiscountPolicy discountPolicy = DiscountPolicy.NONE;
//        ItemSaveDto itemSaveDto = new ItemSaveDto(itemName, price, discountPolicy.getValue());
//        productService.addItem(itemSaveDto);
        final long itemId = 1L;

        //when
        ItemDto itemDto = productService.getItem(itemId);

        //then
        assertThat(itemDto).isNotNull();
        assertThat(itemDto.getItemName()).isEqualTo(itemName);
        assertThat(itemDto.getPrice()).isEqualTo(price);
        assertThat(itemDto.getDiscountPolicy()).isEqualTo(discountPolicy.getValue());


    }
}
