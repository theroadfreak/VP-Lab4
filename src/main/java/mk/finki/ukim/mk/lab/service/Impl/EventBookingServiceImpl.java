package mk.finki.ukim.mk.lab.service.Impl;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.repository.inMemory.InMemEventBookingRepository;
import mk.finki.ukim.mk.lab.repository.inMemory.InMemEventRepository;
import mk.finki.ukim.mk.lab.repository.jpa.EventBookingRepository;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventBookingServiceImpl implements EventBookingService {
    private final EventBookingRepository eventBookingRepository;
    private final EventRepository eventRepository;

    public EventBookingServiceImpl(EventBookingRepository eventBookingRepository, EventRepository eventRepository) {
        this.eventBookingRepository = eventBookingRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Optional<EventBooking> placeBooking(User user, String eventName, String attendeeName, String attendeeAddress, int numberOfTickets) {
        LocalDateTime now = LocalDateTime.now();
        EventBooking eventBooking = new EventBooking(eventName, attendeeName, attendeeAddress, (long) numberOfTickets, user, now);
        eventBookingRepository.save(eventBooking);
        return Optional.of(eventBooking);
    }

    @Override
    public List<EventBooking> listAll() {
        return List.of();
    }

    @Override
    public List<EventBooking> searchEvents(String text) {
        return List.of();
    }

    @Override
    public List<EventBooking> findByUser(String username) {
        return eventBookingRepository.findAllByUser_Username(username);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        EventBooking eventBooking = eventBookingRepository.findById(bookingId).get();
        eventBooking.setStatus("Cancelled");
        Event event = eventRepository.findByName(eventBooking.getEventName());
        event.setNumTickets((int) (event.getNumTickets() + eventBooking.getNumberOfTickets()));
        eventRepository.save(event);
        eventBookingRepository.save(eventBooking);
    }
}
