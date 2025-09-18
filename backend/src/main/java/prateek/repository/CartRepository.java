package prateek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prateek.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
