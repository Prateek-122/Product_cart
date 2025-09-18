package prateek.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prateek.entity.Promotion;
import prateek.service.PromotionService;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping
    public ResponseEntity<List<Promotion>> findAll() {
        return ResponseEntity.ok(promotionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> findById(@PathVariable Long id) {
        return promotionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Promotion> create(@RequestBody Promotion promotion) {
        return ResponseEntity.ok(promotionService.save(promotion));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Promotion> update(@PathVariable Long id, @RequestBody Promotion promotion) {
        promotion.setId(id);
        return ResponseEntity.ok(promotionService.save(promotion));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        promotionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
