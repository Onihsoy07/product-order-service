package com.example.productorderservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ItemSaveDto {

    @NotBlank(message = "상품명을 넣어주세요.")
    private String itemName;
    @Min(value = 0, message = "금액은 0원 이상 넣어주세요.")
    private int price;
    @NotBlank(message = "할인 정책을 넣어주세요.")
    private String discountPolicy;

}
