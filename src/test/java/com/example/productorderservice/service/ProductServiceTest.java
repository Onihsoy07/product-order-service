package com.example.productorderservice.service;

import com.example.productorderservice.domain.dto.ItemSaveDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void 상품등록() {
        ItemSaveDto itemSaveDto = new ItemSaveDto("상품명", 9999, "none");

        productService.addItem(itemSaveDto);
    }
}
