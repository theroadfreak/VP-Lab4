package mk.finki.ukim.mk.lab.repository.inMemory;

import mk.finki.ukim.mk.lab.Bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemCategoryRepository {

    public List<Category> listAll() {
        return DataHolder.categories;
    }

    public Optional<Category> findById(Long id) {
        return DataHolder.categories.stream().filter(i -> i.getId().equals(id)).findFirst();
    }
}
