package prateek.service;

import prateek.entity.PaymentMethod;

public interface PaymentGatewayClient {
    String charge(String orderReference, double amount, PaymentMethod method);
}
