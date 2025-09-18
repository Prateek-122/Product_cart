package prateek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prateek.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
