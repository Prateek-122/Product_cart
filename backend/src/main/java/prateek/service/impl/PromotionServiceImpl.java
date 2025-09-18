package prateek.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prateek.entity.Promotion;
import prateek.repository.PromotionRepository;
import prateek.service.PromotionService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    public Promotion save(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    @Override
    public Optional<Promotion> findById(Long id) {
        return promotionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        promotionRepository.deleteById(id);
    }
}
