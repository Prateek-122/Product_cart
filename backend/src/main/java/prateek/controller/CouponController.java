package prateek.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import prateek.dto.CouponRequest;
import prateek.dto.CouponResponse;
import prateek.entity.Coupon;
import prateek.entity.Product;
import prateek.entity.User;
import prateek.service.CouponService;
import prateek.service.ProductService;
import prateek.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
public class CouponController {

    private final CouponService couponService;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CouponResponse> create(@RequestBody CouponRequest request) {
        User user = userService.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productService.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Coupon coupon = new Coupon();
        coupon.setCode(request.getCode());
        coupon.setDiscountType(request.getDiscountType());
        coupon.setDiscountValue(request.getDiscountValue());
        coupon.setUser(user);
        coupon.setProduct(product);
        coupon.setValidFrom(request.getValidFrom());
        coupon.setValidTo(request.getValidTo());

        Coupon saved = couponService.save(coupon);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CouponResponse>> findAll() {
        return ResponseEntity.ok(couponService.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/me")
    public ResponseEntity<List<CouponResponse>> findForCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(couponService.findByUserId(user.getId()).stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CouponResponse>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(couponService.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    private CouponResponse toResponse(Coupon coupon) {
        return CouponResponse.builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .discountType(coupon.getDiscountType())
                .discountValue(coupon.getDiscountValue())
                .userId(coupon.getUser() != null ? coupon.getUser().getId() : null)
                .userEmail(coupon.getUser() != null ? coupon.getUser().getEmail() : null)
                .productId(coupon.getProduct() != null ? coupon.getProduct().getId() : null)
                .productTitle(coupon.getProduct() != null ? coupon.getProduct().getTitle() : null)
                .validFrom(coupon.getValidFrom())
                .validTo(coupon.getValidTo())
                .redeemed(coupon.isRedeemed())
                .build();
    }
}
