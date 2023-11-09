package com.example.productorderservice.service;

import com.example.productorderservice.config.ApiTestConfig;
import com.example.productorderservice.domain.dto.ItemDto;
import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.domain.dto.ItemUpdateDto;
import com.example.productorderservice.domain.dto.OrderSaveDto;
import com.example.productorderservice.domain.enumeration.DiscountPolicy;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//트랜잭션 사용 시 Item 사라짐? DataBaseCleanUp 때문?
//@Transactional
public class OrdersApiTest extends ApiTestConfig {

    @Autowired
    private ItemService itemService;
    @Autowired
    private OrdersService ordersService;

    @BeforeEach
    void initAddItem() {
        itemService.addItem(new ItemSaveDto("상품명", 9999, DiscountPolicy.NONE.getValue()));
    }

    @Test
    void 주문생성() {
        final OrderSaveDto orderSaveDto = new OrderSaveDto(1L, 1234);

        //API 요청
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderSaveDto)
                .when()
                .post("/orders")
                .then()
                .log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

}
