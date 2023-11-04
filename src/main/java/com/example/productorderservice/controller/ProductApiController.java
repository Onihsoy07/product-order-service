package com.example.productorderservice.controller;

import com.example.productorderservice.domain.dto.ItemDto;
import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> addItem(@Valid @RequestBody final ItemSaveDto itemSaveDto,
                                        BindingResult bindingResult) {
        productService.addItem(itemSaveDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable("itemId") Long itemId) {
        ItemDto itemDto = productService.getItem(itemId);

        return ResponseEntity.status(HttpStatus.OK).body(itemDto);
    }


}
