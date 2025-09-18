package prateek.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prateek.entity.Payment;
import prateek.entity.PaymentStatus;
import prateek.repository.OrderRepository;
import prateek.repository.PaymentRepository;
import prateek.service.PaymentGatewayClient;
import prateek.service.PaymentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentGatewayClient paymentGatewayClient;
    private final OrderRepository orderRepository;

    @Override
    public Payment save(Payment payment) {
        String gatewayReference = paymentGatewayClient.charge(
                "ORDER-" + payment.getOrder().getId(),
                payment.getAmount().doubleValue(),
                payment.getMethod());
        payment.setGatewayResponse(gatewayReference);
        payment.setStatus(PaymentStatus.SUCCESS);
        Payment saved = paymentRepository.save(payment);
        payment.getOrder().setPaymentId(saved.getId());
        orderRepository.save(payment.getOrder());
        return saved;
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
}
