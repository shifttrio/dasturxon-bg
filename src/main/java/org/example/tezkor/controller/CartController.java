package org.example.tezkor.controller;

import lombok.RequiredArgsConstructor;
import org.example.tezkor.dto.CartRequestDto;
import org.example.tezkor.dto.CartUpdateDto;
import org.example.tezkor.enums.CartItem;
import org.example.tezkor.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin
public class CartController {

    private final CartService cartService;

    // ADD TO CART
    @PostMapping("/add")
    public void add(@RequestBody CartRequestDto dto) {

        cartService.addToCart(
                dto.getPhone(),
                dto.getShopId(),
                dto.getProductId(),
                dto.getPrice(),
                dto.getQuantity()
        );
    }

    // GET CART (restaurant-wise)
    @GetMapping
    public List<CartItem> get(@RequestParam String phone, @RequestParam Long shopId) {
        return cartService.getCartItems(phone, shopId);
    }


    // UPDATE QTY
    @PostMapping("/update")
    public void update(@RequestBody CartUpdateDto dto) {
        cartService.changeQuantity(dto.getCartId(), dto.getProductId(), dto.getQuantity());
    }
}
