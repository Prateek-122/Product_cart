package prateek.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String sku;
    private String title;
    private String description;
    private Long categoryId;
    private String attributesJson;
    private Long sellerId;
}
