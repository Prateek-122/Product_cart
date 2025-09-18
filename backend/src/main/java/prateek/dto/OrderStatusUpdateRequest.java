package prateek.dto;

import lombok.Getter;
import lombok.Setter;
import prateek.entity.OrderStatus;

@Getter
@Setter
public class OrderStatusUpdateRequest {
    private OrderStatus status;
}
