package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String category;

    public Category(String category) {
        this.category = category;
    }

    public Category() {

    }

    public boolean equals(Category c) {
        return category.equals(c.category);
    }

    @Override
    public String toString() {
        return category;
    }
}
