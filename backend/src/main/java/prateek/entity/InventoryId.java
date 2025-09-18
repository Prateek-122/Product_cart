package prateek.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class InventoryId implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "warehouse_id")
    private String warehouseId;
}
