package prateek.service;

import prateek.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment save(Payment payment);
    List<Payment> findAll();
    Optional<Payment> findById(Long id);
    void delete(Long id);
}
