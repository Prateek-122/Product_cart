package prateek.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prateek.entity.Inventory;
import prateek.entity.InventoryId;
import prateek.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Inventory>> findAll() {
        return ResponseEntity.ok(inventoryService.findAll());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Inventory>> findByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.findByProductId(productId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Inventory> create(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.save(inventory));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Inventory> update(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.save(inventory));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@RequestBody InventoryId id) {
        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
