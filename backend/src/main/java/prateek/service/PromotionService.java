package prateek.service;

import prateek.entity.Promotion;

import java.util.List;
import java.util.Optional;

public interface PromotionService {
    Promotion save(Promotion promotion);
    List<Promotion> findAll();
    Optional<Promotion> findById(Long id);
    void delete(Long id);
}
