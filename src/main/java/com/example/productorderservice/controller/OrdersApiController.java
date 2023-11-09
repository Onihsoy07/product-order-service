package com.example.productorderservice.controller;

import com.example.productorderservice.domain.dto.OrderSaveDto;
import com.example.productorderservice.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersApiController {

    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<Void> saveOrders(@Valid @RequestBody final OrderSaveDto orderSaveDto,
                                           BindingResult bindingResult) {
        ordersService.saveOrders(orderSaveDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
