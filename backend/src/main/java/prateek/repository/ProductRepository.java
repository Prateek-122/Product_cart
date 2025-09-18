package prateek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prateek.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "WHERE (:keyword IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:categoryId IS NULL OR p.category.id = :categoryId)")
    List<Product> searchProducts(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);
}
