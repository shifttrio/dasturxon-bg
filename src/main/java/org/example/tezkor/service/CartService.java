package org.example.tezkor.service;

import lombok.RequiredArgsConstructor;
import org.example.tezkor.enums.Cart;
import org.example.tezkor.enums.CartItem;
import org.example.tezkor.enums.CartStatus;
import org.example.tezkor.repository.CartItemRepository;
import org.example.tezkor.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // ACTIVE cart topiladi yoki yaratiladi
    public Cart getOrCreateCart(String phone, Long shopId) {
        return cartRepository
                .findByPhoneNumberAndShopIdAndStatus(phone, shopId, CartStatus.ACTIVE)
                .orElseGet(() -> {
                    Cart cart = Cart.builder()
                            .phoneNumber(phone)
                            .shopId(shopId)
                            .status(CartStatus.ACTIVE)
                            .build();
                    return cartRepository.save(cart);
                });
    }



    // Add to cart
    public void addToCart(String phone, Long shopId, Long productId, Double price, Integer qty) {

        Cart cart = getOrCreateCart(phone, shopId);

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElse(null);

        if (item == null) {
            item = CartItem.builder()
                    .cartId(cart.getId())
                    .productId(productId)
                    .price(price)
                    .quantity(qty)
                    .build();
        } else {
            item.setQuantity(item.getQuantity() + qty);
        }

        cartItemRepository.save(item);
    }

    // Plus minus
    public void changeQuantity(Long cartId, Long productId, Integer qty) {

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cartId, productId)
                .orElseThrow();

        if (qty <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(qty);
            cartItemRepository.save(item);
        }
    }

    // Savatni olish
    // Savatni olish (restaurant-wise)
    public List<CartItem> getCartItems(String phone, Long shopId) {

        Cart cart = cartRepository
                .findByPhoneNumberAndShopIdAndStatus(phone, shopId, CartStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartItemRepository.findByCartId(cart.getId());
    }

}
