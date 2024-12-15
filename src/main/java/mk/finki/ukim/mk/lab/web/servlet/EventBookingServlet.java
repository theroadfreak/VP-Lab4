package mk.finki.ukim.mk.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "EventBookingServlet", urlPatterns = "/e")
@ServletComponentScan
public class EventBookingServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;

    public EventBookingServlet(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(req.getServletContext());
        WebContext context = new WebContext(application.buildExchange(req, resp));

        context.setVariable("event", req.getParameter("event"));
//        context.setVariable("attendeeName", req.getParameter("attendeeName"));
        context.setVariable("address", req.getRemoteAddr());
        context.setVariable("numTickets", req.getParameter("numTickets"));

        springTemplateEngine.process("bookingConfirmation.html", context, resp.getWriter());
    }
}
