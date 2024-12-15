package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> listAll();

    public Optional<Category> findById(Long Id);
}
