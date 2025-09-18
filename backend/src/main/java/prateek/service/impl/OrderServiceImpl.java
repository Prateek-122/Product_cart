package prateek.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prateek.entity.Order;
import prateek.repository.OrderRepository;
import prateek.service.OrderService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
