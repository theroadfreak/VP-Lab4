package mk.finki.ukim.mk.lab.repository.jpa;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByName(String name);

    List<Event> findByNameContainingOrDescriptionContaining(String name, String description);

    List<Event> findByCategory(Category category);

    List<Event> findByNameContainingAndCategory(String name, Category category);

    @Modifying
    @Query("UPDATE Event e" +
            " SET e.numTickets = e.numTickets - :numTickets " +
            "WHERE e.Id = :eventId AND e.numTickets >= :numTickets")
    void reserveTickets(@Param("eventId") Long eventId, @Param("numTickets") int numTickets);
}
