package mk.finki.ukim.mk.lab.repository.jpa;

import jakarta.transaction.Transactional;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.EventBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventBookingRepository extends JpaRepository<EventBooking, Long> {
    List<EventBooking> searchEventBookingByEventName(String eventName);
    List<EventBooking> findAllByUser_Username(String username);
}
