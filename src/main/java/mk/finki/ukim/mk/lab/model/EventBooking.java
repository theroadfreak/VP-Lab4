package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
public class EventBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String eventName;
    private String attendeeName;
    private String attendeeAddress;
    private Long numberOfTickets;
    private String bookingDate;
    private String status;

    @ManyToOne
    private User user;

    public EventBooking(String eventName, String attendeeName, String attendeeAddress,
                        Long numberOfTickets, User user, LocalDateTime bookingDate) {
        this.eventName = eventName;
        this.attendeeName = attendeeName;
        this.attendeeAddress = attendeeAddress;
        this.numberOfTickets = numberOfTickets;
        this.user = user;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.bookingDate = formatter.format(bookingDate);
        this.status = "Booked";
    }

    public EventBooking() {

    }
}
