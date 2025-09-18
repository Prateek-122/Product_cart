package prateek.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prateek.dto.ProductRequest;
import prateek.entity.Category;
import prateek.entity.Product;
import prateek.entity.User;
import prateek.service.CategoryService;
import prateek.service.ProductService;
import prateek.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam(required = false) String keyword,
                                                @RequestParam(required = false) Long categoryId) {
        return ResponseEntity.ok(productService.search(keyword, categoryId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> create(@RequestBody ProductRequest request) {
        Product product = buildProductFromRequest(new Product(), request);
        return ResponseEntity.ok(productService.save(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Product updated = buildProductFromRequest(product, request);
        updated.setId(id);
        return ResponseEntity.ok(productService.save(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Product buildProductFromRequest(Product product, ProductRequest request) {
        product.setSku(request.getSku());
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setAttributesJson(request.getAttributesJson());
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryService.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        }
        product.setCategory(category);
        if (request.getSellerId() != null) {
            User seller = userService.findById(request.getSellerId())
                    .orElseThrow(() -> new IllegalArgumentException("Seller not found"));
            product.setSeller(seller);
        }
        return product;
    }
}
