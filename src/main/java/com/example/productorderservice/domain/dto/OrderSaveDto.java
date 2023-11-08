package com.example.productorderservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveDto {

    @NotBlank(message = "상품아이디를 넣어주세요.")
    private Long itemId;
    @Min(value = 1, message = "수량은 1개 이상 넣어주세요.")
    private int quantity;
}
