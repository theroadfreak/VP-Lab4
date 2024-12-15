package mk.finki.ukim.mk.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EventSearchServlet", urlPatterns = "/se")
@ServletComponentScan
public class EventSearchServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final EventService eventService;

    public EventSearchServlet(SpringTemplateEngine springTemplateEngine, EventService eventService) {
        this.springTemplateEngine = springTemplateEngine;
        this.eventService = eventService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        String ratingParam = req.getParameter("rating");
        String category = req.getParameter("category");

        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(req.getServletContext());
        WebContext context = new WebContext(application.buildExchange(req, resp));

        Integer rating = (ratingParam != null && !ratingParam.isEmpty()) ? Integer.parseInt(ratingParam) : 0;

        List<Event> events;
        if (category.isEmpty() || category.equals("Choose category")) {
            events = eventService.searchEvents(query);
        } else {
            events = eventService.searchEventsByCategory(category);
            if (query != null && !query.isEmpty()) {
                events.addAll(eventService.searchEvents(query));
            }
        }
        context.setVariable("events", events);

        context.setVariable("rating", rating);
        springTemplateEngine.process("searchResults.html", context, resp.getWriter());
    }
}
