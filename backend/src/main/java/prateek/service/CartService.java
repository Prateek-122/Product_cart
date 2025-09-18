package prateek.service;

import prateek.entity.Cart;

import java.util.Optional;

public interface CartService {
    Cart save(Cart cart);
    Optional<Cart> findByUserId(Long userId);
    void delete(Long userId);
}
