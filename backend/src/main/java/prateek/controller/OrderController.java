package prateek.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import prateek.dto.OrderRequest;
import prateek.dto.OrderStatusUpdateRequest;
import prateek.entity.Order;
import prateek.entity.User;
import prateek.service.OrderService;
import prateek.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Order>> listOrders(Authentication authentication,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_ADMIN"));
        if (isAdmin) {
            return ResponseEntity.ok(orderService.findAll());
        }
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(orderService.findByUserId(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id,
                                          Authentication authentication,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_ADMIN"));
        return orderService.findById(id)
                .filter(order -> isAdmin || order.getUser().getEmail().equals(userDetails.getUsername()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(403).build());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestBody OrderRequest request) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Order order = new Order();
        order.setUser(user);
        order.setOrderItemsJson(request.getItemsJson());
        order.setTotal(request.getTotal());
        return ResponseEntity.ok(orderService.save(order));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestBody OrderStatusUpdateRequest request) {
        Order order = orderService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(request.getStatus());
        return ResponseEntity.ok(orderService.save(order));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
