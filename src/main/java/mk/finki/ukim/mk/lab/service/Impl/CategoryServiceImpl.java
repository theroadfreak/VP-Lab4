package mk.finki.ukim.mk.lab.service.Impl;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.repository.inMemory.InMemCategoryRepository;
import mk.finki.ukim.mk.lab.repository.jpa.CategoryRepository;
import mk.finki.ukim.mk.lab.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long Id) {
        return categoryRepository.findAll().stream().filter(e -> e.getId().equals(Id)).findFirst();
    }
}
