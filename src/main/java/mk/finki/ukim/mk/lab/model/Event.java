package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String description;
    private double popularityScore;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Location location;

    private boolean liked;
    private int numTickets;

    public Event(String name, String description, double popularityScore, Category category,
                 Location location, int numTickets) {
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.category = category;
        this.location = location;
        this.liked = false;
        this.numTickets = numTickets;
    }

    public Event() {

    }

    @Override
    public String toString() {
        return "Name=" + name +
                ", Description:" + description +
                ", Rating" + popularityScore;
    }
}
