package prateek.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import prateek.dto.OrderRequest;
import prateek.dto.OrderStatusUpdateRequest;
import prateek.entity.Order;
import prateek.entity.User;
import prateek.service.OrderService;
import prateek.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/lookup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> lookupByContact(@RequestParam(required = false) String email,
                                                       @RequestParam(value = "whatsapp", required = false) String whatsappNumber) {
        if (!StringUtils.hasText(email) && !StringUtils.hasText(whatsappNumber)) {
            return ResponseEntity.badRequest().build();
        }
        Map<Long, Order> results = new LinkedHashMap<>();
        if (StringUtils.hasText(email)) {
            orderService.findByUserEmail(email)
                    .forEach(order -> results.put(order.getId(), order));
        }
        if (StringUtils.hasText(whatsappNumber)) {
            String phoneHash = DigestUtils.md5DigestAsHex(whatsappNumber.getBytes(StandardCharsets.UTF_8));
            orderService.findByUserPhoneHash(phoneHash)
                    .forEach(order -> results.put(order.getId(), order));
        }
        return ResponseEntity.ok(new ArrayList<>(results.values()));
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
