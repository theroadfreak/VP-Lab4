package mk.finki.ukim.mk.lab.service.Impl;

import jakarta.transaction.Transactional;
import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.exeptions.CategoryNotFoundException;
import mk.finki.ukim.mk.lab.model.exeptions.LocationNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.CategoryRepository;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepository;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    public EventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String text) {
        return eventRepository.findByNameContainingOrDescriptionContaining(text, text);
    }

    @Override
    public List<Event> searchEventsByCategory(String category) {
        return eventRepository.findByCategory(categoryRepository.findByCategory(category));
    }

    @Override
    public List<Event> searchEventsByNameAndCategory(String text, String category) {
        return eventRepository.findByNameContainingAndCategory(text, categoryRepository.findByCategory(category));
    }

    @Override
    public Optional<Event> findById(Long Id) {
        return eventRepository.findAll().stream().filter(e -> e.getId().equals(Id)).findFirst();
    }

    @Override
    public Optional<Event> findByName(String name) {
        return eventRepository.findAll().stream().filter(e -> e.getName().equals(name)).findFirst();
    }

    @Override
    public void save(String name, String description, double popularityScore,
                                Long categoryId, Long locationId, int numTickets) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Location location = this.locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException(locationId));

        eventRepository.save(new Event(name, description, popularityScore, category, location, numTickets));
    }

    @Override
    public void update(Long eventId, String name, String description, double popularityScore, Long categoryId, Long locationId, int numTickets) {
        Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));
        Location location = this.locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found: " + locationId));

        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(popularityScore);
        event.setCategory(category);
        event.setLocation(location);
        event.setNumTickets(numTickets);
        this.eventRepository.save(event);
    }

    @Override
    public void deleteById(Long Id) {
        eventRepository.deleteById(Id);
    }

    @Override
    public void like(Long Id) {
        if (findById(Id).isPresent()) {
            findById(Id).get().setLiked(true);
        }
    }

    @Override
    @Transactional
    public void reserveCard(Long eventId, int numTickets) {
        this.eventRepository.reserveTickets(eventId, numTickets);
    }
}
