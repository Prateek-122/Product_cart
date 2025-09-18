package prateek.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import prateek.entity.PaymentMethod;
import prateek.service.PaymentGatewayClient;

import java.util.UUID;

@Component
@Slf4j
public class MockPaymentGatewayClient implements PaymentGatewayClient {

    @Override
    public String charge(String orderReference, double amount, PaymentMethod method) {
        String transactionId = UUID.randomUUID().toString();
        log.info("Mock payment processed for order {} using {} with amount {}", orderReference, method, amount);
        return "TXN-" + transactionId;
    }
}
