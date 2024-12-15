package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class EventBookingController {
    private final EventBookingService eventBookingService;
    private final EventService eventService;
    private final UserService userService;

    public EventBookingController(EventBookingService eventBookingService, EventService eventService, UserService userService) {
        this.eventBookingService = eventBookingService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/bookings")
    public String getEventBookingPage(HttpServletRequest request, Model model) {
        try {
            String username = request.getRemoteUser();
            if (username == null) {
                return "redirect:/login";
            }
            List<EventBooking> bookings = this.eventBookingService.findByUser(username);
            
            // Calculate statistics
            long totalBookings = bookings.size();
            long activeBookings = bookings.stream()
                    .filter(b -> !"Cancelled".equals(b.getStatus()))
                    .count();
            long cancelledBookings = bookings.stream()
                    .filter(b -> "Cancelled".equals(b.getStatus()))
                    .count();

            model.addAttribute("bookings", bookings);
            model.addAttribute("totalBookings", totalBookings);
            model.addAttribute("activeBookings", activeBookings);
            model.addAttribute("cancelledBookings", cancelledBookings);
            
            return "userBookings";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while loading your bookings.");
            return "redirect:/events";
        }
    }

    @PostMapping("/bookings/cancel/{id}")
    public String cancelBooking(@PathVariable("id") Long bookingId) {
        try {
            eventBookingService.cancelBooking(bookingId);
            return "redirect:/bookings";
        } catch (Exception e) {
            return "redirect:/bookings?error=Failed to cancel booking";
        }
    }

    @PostMapping("/eventBooking")
    public String bookEvent(@RequestParam String eventName,
                            @RequestParam int numTickets,
                            Model model,
                            HttpServletRequest request) {
        try {
            String username = request.getRemoteUser();
            if (username == null) {
                return "redirect:/login";
            }

            Optional<Event> eventOpt = eventService.findByName(eventName);
            if (eventOpt.isEmpty()) {
                return "redirect:/events?error=Event not found";
            }

            Event event = eventOpt.get();
            if (event.getNumTickets() < numTickets) {
                return "redirect:/events?error=Not enough available tickets!";
            }

            User user = userService.findByUsername(username);
            Optional<EventBooking> booking = eventBookingService.placeBooking(user, eventName, user.getName(), user.getAddress(), numTickets);
            
            if (booking.isPresent()) {
                model.addAttribute("name", user.getName());
                model.addAttribute("event", eventName);
                model.addAttribute("address", user.getAddress());
                model.addAttribute("numTickets", numTickets);
                model.addAttribute("bookingDate", booking.get().getBookingDate());
                eventService.reserveCard(event.getId(), numTickets);
                return "bookingConfirmation";
            } else {
                return "redirect:/events?error=Failed to create booking";
            }
        } catch (Exception e) {
            return "redirect:/events?error=An unexpected error occurred";
        }
    }
}
