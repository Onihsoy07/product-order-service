package com.example.productorderservice.service;

import com.example.productorderservice.config.ApiTestConfig;
import com.example.productorderservice.domain.dto.ItemDto;
import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.domain.dto.OrdersSaveDto;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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
        final OrdersSaveDto ordersSaveDto = new OrdersSaveDto(1L, 1234);

        //API 요청
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(ordersSaveDto)
                .when()
                .post("/orders")
                .then()
                .log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 주문조회() {
        //given
        final Long ordersId = 1L;
        주문생성();

        //when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .get("/orders/{ordersId}", ordersId)
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong("id")).isEqualTo(ordersId);
        assertThat(response.jsonPath().getInt("quantity")).isEqualTo(1234);
        assertThat(response.jsonPath().getObject("itemDto", ItemDto.class)).isNotNull();



    }

}
