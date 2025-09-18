package prateek.service;

import prateek.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);
    List<Order> findAll();
    Optional<Order> findById(Long id);
    List<Order> findByUserId(Long userId);
    List<Order> findByUserEmail(String email);
    List<Order> findByUserPhoneHash(String phoneHash);
    void delete(Long id);
}
