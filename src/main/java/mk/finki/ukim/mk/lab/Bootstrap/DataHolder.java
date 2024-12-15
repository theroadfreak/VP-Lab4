package mk.finki.ukim.mk.lab.Bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.enumerations.Role;
import mk.finki.ukim.mk.lab.repository.jpa.CategoryRepository;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepository;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Event> events = null;
    public static List<Category> categories = null;
    public static List<Location> locations = null;
    public static List<User> users = null;

    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public DataHolder(CategoryRepository categoryRepository, LocationRepository locationRepository, EventRepository eventRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void init() {
        categories = new ArrayList<>();
        if (this.categoryRepository.count() == 0) {
            categories.add(new Category("Expo"));
            categories.add(new Category("Festival"));
            categories.add(new Category("Competition"));
            categories.add(new Category("Other"));
            this.categoryRepository.saveAll(categories);
        } else {
            categories = this.categoryRepository.findAll();
        }

        locations = new ArrayList<>();
        if (locationRepository.count() == 0) {
            locations.add(new Location("Tech Haven",
                    "123 Byte Boulevard, Silicon City, CA 94010",
                    "150",
                    "Tech Haven is a spacious, high-tech venue designed for hackathons and coding workshops."));
            locations.add(new Location("Code & Brew",
                    "456 Java Lane, Maple-wood, NJ 07040",
                    "50",
                    "A cozy, casual space in the heart of Maple-wood, Code & Brew combines a café vibe with tech amenities."));
            locations.add(new Location("Festival Grounds",
                    "1001 Celebration Drive, Orlando, FL 32801",
                    "5000",
                    "Festival Grounds is an expansive outdoor venue."));
            locations.add(new Location("Competitor’s Coliseum",
                    "555 Victory Lane, Las Vegas, NV 89109",
                    "7000",
                    "Competitor’s Coliseum is a massive, arena-style venue in Las Vegas."));
            locations.add(new Location("Expo Center Park",
                    "300 Expo Boulevard, Austin, TX 78702",
                    "10000",
                    "Expo Center Park is a versatile event space with both indoor and outdoor areas."));
            this.locationRepository.saveAll(locations);
        } else {
            locations = this.locationRepository.findAll();
        }

        events = new ArrayList<>();
        if (eventRepository.count() == 0) {
            events.add(new Event("Web Development Conference 2024",
                    "A premier conference for web developers featuring workshops on React, Angular, Node.js, and the latest web technologies. Includes networking sessions and hands-on labs.",
                    95,
                    categories.get(0),
                    locations.get(0),
                    200));
            events.add(new Event("Electronic Music Festival",
                    "An immersive electronic music experience featuring top DJs, light shows, and interactive art installations. Multiple stages and genres represented.",
                    90,
                    categories.get(1),
                    locations.get(2),
                    2000));
            events.add(new Event("Hackathon 2024",
                    "48-hour coding challenge where teams compete to build innovative solutions for real-world problems. Prizes include startup funding and mentorship opportunities.",
                    85,
                    categories.get(2),
                    locations.get(0),
                    150));
            events.add(new Event("AI & Machine Learning Summit",
                    "Industry leaders share insights on artificial intelligence, deep learning, and the future of technology. Featuring keynote speakers from major tech companies.",
                    92,
                    categories.get(0),
                    locations.get(4),
                    300));
            events.add(new Event("Street Food & Culture Festival",
                    "Celebration of global cuisines with food vendors, cooking demonstrations, cultural performances, and interactive workshops.",
                    88,
                    categories.get(1),
                    locations.get(2),
                    1000));
            events.add(new Event("Startup Pitch Day",
                    "Early-stage startups present their business ideas to venture capitalists and angel investors. Networking opportunities and mentorship sessions included.",
                    85,
                    categories.get(2),
                    locations.get(1),
                    100));
            events.add(new Event("Digital Art Exhibition",
                    "Showcase of digital art, NFTs, and interactive installations. Meet artists, attend workshops, and experience virtual reality art.",
                    87,
                    categories.get(3),
                    locations.get(4),
                    250));
            events.add(new Event("Cybersecurity Conference",
                    "Expert-led sessions on the latest in cybersecurity, ethical hacking, and digital privacy. Includes hands-on security workshops and CTF competitions.",
                    94,
                    categories.get(0),
                    locations.get(0),
                    180));
            events.add(new Event("Gaming Tournament",
                    "Competitive gaming event featuring popular esports titles. Professional players, amateur tournaments, and gaming hardware showcase.",
                    91,
                    categories.get(2),
                    locations.get(3),
                    500));
            events.add(new Event("Tech Career Fair",
                    "Connect with leading tech companies, attend career development workshops, and participate in on-site interviews. Perfect for students and professionals.",
                    86,
                    categories.get(0),
                    locations.get(4),
                    300));
            this.eventRepository.saveAll(events);
        }

        users = new ArrayList<>();
        if (userRepository.count() == 0) {
            users.add(new User("dushan", passwordEncoder.encode("dushan"), "Dushan", "Cimbaljevic", "Kocani", Role.ROLE_USER));
            users.add(new User("admin", passwordEncoder.encode("admin"), "admin", "admin", "-", Role.ROLE_ADMIN));
            this.userRepository.saveAll(users);
        }
    }
}
