package mk.finki.ukim.mk.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.mk.lab.repository.inMemory.InMemEventRepository;
import mk.finki.ukim.mk.lab.service.CategoryService;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import java.io.IOException;

@WebServlet(name = "EventListServlet", urlPatterns = "")
@ServletComponentScan
public class EventListServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final EventService eventService;
    private final CategoryService categoryService;
    public EventListServlet(InMemEventRepository inMemEventRepository, SpringTemplateEngine springTemplateEngine, EventService eventService, CategoryService categoryService) {
        this.springTemplateEngine = springTemplateEngine;
        this.eventService = eventService;
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(req.getServletContext());
        WebContext context = new WebContext(application.buildExchange(req, resp));

        context.setVariable("events", eventService.listAll());
        context.setVariable("categories", categoryService.listAll());

/*
        springTemplateEngine.process("listEvents.html", context, resp.getWriter());
*/
    }
}
