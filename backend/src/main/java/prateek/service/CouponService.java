package prateek.service;

import prateek.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    Coupon save(Coupon coupon);
    List<Coupon> findAll();
    Optional<Coupon> findById(Long id);
    Optional<Coupon> findByCode(String code);
    List<Coupon> findByUserId(Long userId);
    void delete(Long id);
}
