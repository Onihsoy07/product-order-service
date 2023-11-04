package com.example.productorderservice.service;

import com.example.productorderservice.config.ApiTestConfig;
import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.domain.enumeration.DiscountPolicy;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ProductApiTest extends ApiTestConfig {

    @Autowired
    private ProductService productService;

    @Test
    void 상품등록() {
        final ItemSaveDto itemSaveDto = new ItemSaveDto("상품명", 9999, DiscountPolicy.NONE.getValue());

        //API 요청
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemSaveDto)
                .when()
                .post("/products")
                .then()
                .log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 상품조회() {
        상품등록();
        Long itemId = 1L;

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .get("/products/{itemId}", itemId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("itemName")).isEqualTo("상품명");
        assertThat(response.jsonPath().getInt("price")).isEqualTo(9999);
        assertThat(response.jsonPath().getString("discountPolicy")).isEqualTo(DiscountPolicy.NONE.getValue());

    }

}
