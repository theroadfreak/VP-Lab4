package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.CategoryService;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.LocationService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final CategoryService categoryService;
    private final LocationService locationService;

    public EventController(EventService eventService,
                           CategoryService categoryService,
                           LocationService locationService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
        this.locationService = locationService;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model,
                                HttpSession session, HttpServletRequest request) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Event> events = this.eventService.listAll();
        List<Category> categories = this.categoryService.listAll();
        model.addAttribute("events", events);
        model.addAttribute("categories", categories);
        String username = request.getRemoteUser();
        model.addAttribute("user", username);
        return "listEvents";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEditEventForm(@PathVariable Long id, Model model) {
        if (this.eventService.findById(id).isPresent()) {
            Event event = this.eventService.findById(id).get();
            List<Category> categories = this.categoryService.listAll();
            List<Location> locations = this.locationService.findAll();
            model.addAttribute("event", event);
            model.addAttribute("categories", categories);
            model.addAttribute("locations", locations);
            return "addEvent";
        }
        return "redirect:/events?error=EventNotFound";
    }

    @PostMapping("/edit/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editEvent(@PathVariable Long eventId,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long categoryId,
                            @RequestParam Long locationId,
                            @RequestParam int tickets) {

        if (eventId != null) {
            this.eventService.update(eventId, name, description, popularityScore, categoryId, locationId, tickets);
        } else {
            this.eventService.save(name, description, popularityScore, categoryId, locationId, tickets);
        }
        return "redirect:/events";
    }


    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        this.eventService.deleteById(id);
        return "redirect:/events";
    }

    @PostMapping("/like/{id}")
    public String like(@PathVariable Long id) {
        this.eventService.like(id);
        return "redirect:/events";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddEventPage(Model model) {
        List<Category> categories = this.categoryService.listAll();
        List<Location> locations = this.locationService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("locations", locations);
        return "addEvent";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveEvent(@RequestParam(required = false) Long id,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam(name = "rating") double popularityScore,
                            @RequestParam Long category,
                            @RequestParam Long location,
                            @RequestParam int tickets) {
        if (eventService.findById(id).isPresent()) {
            eventService.update(id, name, description, popularityScore, category, location, tickets);
        } else {
            this.eventService.save(name, description, popularityScore, category, location, tickets);
        }
        return "redirect:/events";
    }

    @PostMapping("/search")
    public String searchEvents(@RequestParam(required = false) String queryParam,
                               @RequestParam(required = false) String ratingParam,
                               @RequestParam(required = false) String categoryParam,
                               Model model) {

        Integer rating = (ratingParam != null && !ratingParam.isEmpty()) ? Integer.parseInt(ratingParam) : 0;
        List<Event> events = new ArrayList<>();
        if (queryParam != null && !queryParam.isEmpty() && categoryParam != null &&
                !categoryParam.isEmpty() && !categoryParam.equals("Choose category")) {

            events = this.eventService.searchEventsByNameAndCategory(queryParam, categoryParam);
        } else if (queryParam != null && !queryParam.isEmpty()) {

            events = this.eventService.searchEvents(queryParam);
        } else if (categoryParam != null && !categoryParam.isEmpty()
                && !categoryParam.equals("Choose category")) {

            events = this.eventService.searchEventsByCategory(categoryParam);
        }

        model.addAttribute("events", events);
        model.addAttribute("rating", rating);

        return "searchResults";
    }

    @GetMapping("/accessDenied")
    public String getAccessDeniedPage(Model model) {
        return "accessDenied";
    }

}
