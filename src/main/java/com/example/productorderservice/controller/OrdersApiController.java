package com.example.productorderservice.controller;

import com.example.productorderservice.domain.dto.OrdersDto;
import com.example.productorderservice.domain.dto.OrdersSaveDto;
import com.example.productorderservice.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersApiController {

    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<Void> saveOrders(@Valid @RequestBody final OrdersSaveDto ordersSaveDto,
                                           BindingResult bindingResult) {
        ordersService.saveOrders(ordersSaveDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{ordersId}")
    public ResponseEntity<OrdersDto> getOrders(@PathVariable("ordersId") final Long ordersId) {
        OrdersDto ordersDto = ordersService.getOrders(ordersId);

        return ResponseEntity.status(HttpStatus.OK).body(ordersDto);
    }

    @DeleteMapping("/{ordersId}")
    public ResponseEntity<Void> deleteOrders(@PathVariable("ordersId") final Long ordersId) {
        ordersService.deleteOrders(ordersId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
