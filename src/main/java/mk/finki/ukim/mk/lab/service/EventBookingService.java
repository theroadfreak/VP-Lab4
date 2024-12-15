package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.model.User;

import java.util.List;
import java.util.Optional;

public interface EventBookingService {
    Optional<EventBooking> placeBooking(User user, String eventName, String attendeeName, String attendeeAddress, int numberOfTickets);
    List<EventBooking> listAll();
    List<EventBooking> searchEvents(String text);
    List<EventBooking> findByUser(String username);
    void cancelBooking(Long bookingId);
}
