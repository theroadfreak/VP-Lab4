package mk.finki.ukim.mk.lab.repository.inMemory;

import mk.finki.ukim.mk.lab.Bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemEventRepository {

    public List<Event> findAll() {
        return DataHolder.events.stream()
                .sorted(Comparator.comparing(Event::getId))
                .toList();
    }

    public Optional<Event> findById(Long Id) {
        return DataHolder.events.stream().filter(i -> i.getId().equals(Id)).findFirst();
    }

    public List<Event> searchEvents(String text) {
        List<Event> newList = new ArrayList<>();
        DataHolder.events.forEach(event -> {
            if (event.getName().contains(text) || event.getDescription().contains(text))
                newList.add(event);
        });

        return newList;
    }

    public List<Event> searchEventsByCategory(Category category) {
        List<Event> newList = new ArrayList<>();
        DataHolder.events.forEach(event -> {
            if (event.getCategory().equals(category)) {
                newList.add(event);
            }
        });

        return newList;
    }

    public List<Event> searchEventsByNameAndCategory(String text, String category) {
        List<Event> newList = new ArrayList<>();
        searchEvents(text).forEach(event -> {
            if (event.getCategory().equals(new Category(category))) {
                newList.add(event);
            }
        });

        return newList;
    }


    public Optional<Event> save(String name, String description, double popularityScore,
                                Category category, Location location, int numTickets) {
        if (location == null || category == null) {
            throw new IllegalArgumentException();
        }

        Event event = new Event(name, description, popularityScore, category, location, numTickets);
        DataHolder.events.removeIf(e -> e.getName().equals(name));
        DataHolder.events.add(event);
        return Optional.of(event);
    }

    public void deleteById(Long Id) {
        DataHolder.events.removeIf(e -> e.getId().equals(Id));
    }
}
