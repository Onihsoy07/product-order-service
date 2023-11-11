package com.example.productorderservice.service;

import com.example.productorderservice.config.ApiTestConfig;
import com.example.productorderservice.domain.dto.ItemDto;
import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.domain.dto.ItemUpdateDto;
import com.example.productorderservice.domain.enumeration.DiscountPolicy;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ItemApiTest extends ApiTestConfig {

    @Autowired
    private ItemService itemService;

    @Test
    void 상품등록() {
        final ItemSaveDto itemSaveDto = new ItemSaveDto("상품명", 9999, DiscountPolicy.NONE.getValue());

        //API 요청
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemSaveDto)
                .when()
                .post("/items")
                .then()
                .log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 상품조회() {
        상품등록();
        final Long itemId = 1L;

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .get("/items/{itemId}", itemId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("itemName")).isEqualTo("상품명");
        assertThat(response.jsonPath().getInt("price")).isEqualTo(9999);
        assertThat(response.jsonPath().getString("discountPolicy")).isEqualTo(DiscountPolicy.NONE.getValue());

    }

    @Test
    void 상품수정() {
        상품등록();
        String updateItemName = "상품명1";
        int updatePrice = 1004;
        DiscountPolicy updateDiscountPolicy = DiscountPolicy.NONE;
        final Long itemId = 1L;
        ItemUpdateDto itemUpdateDto = new ItemUpdateDto(updateItemName, updatePrice, updateDiscountPolicy.getValue());

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemUpdateDto)
                .when()
                .patch("/items/{itemId}", itemId)
                .then().log().all()
                .extract();

        ItemDto itemDto = itemService.getItem(itemId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(itemDto.getItemName()).isEqualTo(updateItemName);
        assertThat(itemDto.getPrice()).isEqualTo(updatePrice);
        assertThat(itemDto.getDiscountPolicy()).isEqualTo(updateDiscountPolicy.getValue());
    }

    @Test
    void 상품제거() {
        //given
        final Long itemId = 1L;
        상품등록();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .delete("/items/{itemId}", itemId)
                .then().log().all()
                .extract();

        //then
        assertThatThrownBy(() -> itemService.getItem(itemId)).isInstanceOf(IllegalArgumentException.class);

    }

}
