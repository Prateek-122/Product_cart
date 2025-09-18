package prateek.service;

import prateek.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category save(Category category);
    List<Category> findAll();
    Optional<Category> findById(Long id);
    void delete(Long id);
}
