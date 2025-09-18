package prateek.service;

import prateek.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);
    List<Product> findAll();
    Optional<Product> findById(Long id);
    void delete(Long id);
    List<Product> search(String keyword, Long categoryId);
}
