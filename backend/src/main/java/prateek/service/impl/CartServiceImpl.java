package prateek.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prateek.entity.Cart;
import prateek.repository.CartRepository;
import prateek.service.CartService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return cartRepository.findById(userId);
    }

    @Override
    public void delete(Long userId) {
        cartRepository.deleteById(userId);
    }
}
