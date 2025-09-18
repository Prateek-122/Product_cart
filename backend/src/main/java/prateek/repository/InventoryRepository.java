package prateek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prateek.entity.Inventory;
import prateek.entity.InventoryId;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryId> {
    List<Inventory> findByIdProductId(Long productId);
}
