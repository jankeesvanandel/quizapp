package nl.jpoint.quizapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.atmosphere.cpr.AtmosphereFramework;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.util.ServletContextFactory;

@WebServlet("/broadcast")
public class BroadcastQuestion extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ServletContext servletContext = ServletContextFactory.getDefault().getServletContext();
        AtmosphereFramework framework = (AtmosphereFramework) servletContext.getAttribute("AtmosphereServlet");
        BroadcasterFactory broadcasterFactory = framework.getBroadcasterFactory();
        AtmosphereResourceFactory atmosphereResourceFactory = framework.atmosphereFactory();

        QuizQuestion question = new QuizQuestion();
        question.setQuestion("What?");
        List<String> options = new ArrayList<>();
        options.add("a");
        options.add("b");
        options.add("c");
        options.add("d");
        question.setOptions(options);

        broadcasterFactory.lookup("/quiz").broadcast(question);
    }
}
