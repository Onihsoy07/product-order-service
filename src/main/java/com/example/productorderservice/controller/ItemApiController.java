package com.example.productorderservice.controller;

import com.example.productorderservice.domain.dto.ItemDto;
import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.domain.dto.ItemUpdateDto;
import com.example.productorderservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Void> addItem(@Valid @RequestBody final ItemSaveDto itemSaveDto,
                                        BindingResult bindingResult) {
        itemService.addItem(itemSaveDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable("itemId") Long itemId) {
        ItemDto itemDto = itemService.getItem(itemId);

        return ResponseEntity.status(HttpStatus.OK).body(itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable("itemId") Long itemId,
                                              @Valid @RequestBody final ItemUpdateDto itemUpdateDto) {
        itemService.updateItem(itemId, itemUpdateDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
