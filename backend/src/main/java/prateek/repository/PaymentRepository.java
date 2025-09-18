package prateek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prateek.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
