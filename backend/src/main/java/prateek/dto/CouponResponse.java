package prateek.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
public class CouponResponse {
    private Long id;
    private String code;
    private String discountType;
    private BigDecimal discountValue;
    private Long userId;
    private String userEmail;
    private Long productId;
    private String productTitle;
    private Instant validFrom;
    private Instant validTo;
    private boolean redeemed;
}
