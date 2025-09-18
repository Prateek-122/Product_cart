package prateek.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import prateek.dto.PaymentRequest;
import prateek.entity.Order;
import prateek.entity.Payment;
import prateek.entity.PaymentStatus;
import prateek.service.OrderService;
import prateek.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Payment>> findAll() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> findById(@PathVariable Long id,
                                            Authentication authentication,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_ADMIN"));
        return paymentService.findById(id)
                .filter(payment -> isAdmin || payment.getOrder().getUser().getEmail().equals(userDetails.getUsername()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(403).build());
    }

    @PostMapping
    public ResponseEntity<Payment> create(@AuthenticationPrincipal UserDetails userDetails,
                                          Authentication authentication,
                                          @RequestBody PaymentRequest request) {
        Order order = orderService.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_ADMIN"));
        if (!isAdmin && !order.getUser().getEmail().equals(userDetails.getUsername())) {
            return ResponseEntity.status(403).build();
        }
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.INITIATED);
        return ResponseEntity.ok(paymentService.save(payment));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
