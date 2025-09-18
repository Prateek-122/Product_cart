package prateek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prateek.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String code);
    List<Coupon> findByUserId(Long userId);
}
