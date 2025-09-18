package prateek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prateek.entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
