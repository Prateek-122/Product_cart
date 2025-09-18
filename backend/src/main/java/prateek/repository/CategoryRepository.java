package prateek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prateek.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
