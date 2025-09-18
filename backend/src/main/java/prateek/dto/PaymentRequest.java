package prateek.dto;

import lombok.Getter;
import lombok.Setter;
import prateek.entity.PaymentMethod;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {
    private Long orderId;
    private BigDecimal amount;
    private PaymentMethod method;
}
