package prateek.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prateek.entity.Inventory;
import prateek.entity.InventoryId;
import prateek.repository.InventoryRepository;
import prateek.service.InventoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public Optional<Inventory> findById(InventoryId id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public void delete(InventoryId id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public List<Inventory> findByProductId(Long productId) {
        return inventoryRepository.findByIdProductId(productId);
    }
}
