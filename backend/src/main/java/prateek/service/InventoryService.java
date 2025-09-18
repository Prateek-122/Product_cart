package prateek.service;

import prateek.entity.Inventory;
import prateek.entity.InventoryId;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    Inventory save(Inventory inventory);
    List<Inventory> findAll();
    Optional<Inventory> findById(InventoryId id);
    void delete(InventoryId id);
    List<Inventory> findByProductId(Long productId);
}
