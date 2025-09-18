package prateek.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import prateek.entity.Cart;
import prateek.entity.User;
import prateek.service.CartService;
import prateek.service.UserService;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return cartService.findByUserId(user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(new Cart()));
    }

    @PutMapping
    public ResponseEntity<Cart> updateCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Cart cart) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        cart.setUser(user);
        cart.setUserId(user.getId());
        return ResponseEntity.ok(cartService.save(cart));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        cartService.delete(user.getId());
        return ResponseEntity.noContent().build();
    }
}
