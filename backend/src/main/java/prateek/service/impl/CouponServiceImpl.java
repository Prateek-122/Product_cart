package prateek.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prateek.entity.Coupon;
import prateek.repository.CouponRepository;
import prateek.service.CouponService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    @Override
    public Optional<Coupon> findById(Long id) {
        return couponRepository.findById(id);
    }

    @Override
    public Optional<Coupon> findByCode(String code) {
        return couponRepository.findByCode(code);
    }

    @Override
    public List<Coupon> findByUserId(Long userId) {
        return couponRepository.findByUserId(userId);
    }

    @Override
    public void delete(Long id) {
        couponRepository.deleteById(id);
    }
}
