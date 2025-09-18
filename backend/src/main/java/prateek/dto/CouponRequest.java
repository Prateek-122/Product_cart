package prateek.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class CouponRequest {
    private String code;
    private String discountType;
    private BigDecimal discountValue;
    private Long userId;
    private Long productId;
    private Instant validFrom;
    private Instant validTo;
}
