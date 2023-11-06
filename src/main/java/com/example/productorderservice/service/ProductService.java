package com.example.productorderservice.service;

import com.example.productorderservice.domain.Item;
import com.example.productorderservice.domain.dto.ItemDto;
import com.example.productorderservice.domain.dto.ItemSaveDto;
import com.example.productorderservice.domain.dto.ItemUpdateDto;
import com.example.productorderservice.domain.enumeration.DiscountPolicy;
import com.example.productorderservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ItemRepository itemRepository;

    public void addItem(ItemSaveDto itemSaveDto) {
        Item item = Item.builder()
                .itemName(itemSaveDto.getItemName())
                .price(itemSaveDto.getPrice())
                .discountPolicy(DiscountPolicy.valueOf(itemSaveDto.getDiscountPolicy().toUpperCase()))
                .build();
        itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public ItemDto getItem(Long itemId) {
        Item item = getItemEntity(itemId);

        return new ItemDto(
                item.getId(),
                item.getItemName(),
                item.getPrice(),
                item.getDiscountPolicy().getValue()
        );
    }

    public void updateItem(Long itemId, ItemUpdateDto itemUpdateDto) {
        Item item = getItemEntity(itemId);
        item.updateItem(itemUpdateDto);
    }


    private Item getItemEntity(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        });
        return item;
    }

}
