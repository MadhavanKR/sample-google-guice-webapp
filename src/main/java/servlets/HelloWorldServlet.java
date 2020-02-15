package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import services.HelloWorldService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class HelloWorldServlet extends HttpServlet {

    private HelloWorldService helloWorldService;

    @Inject
    public HelloWorldServlet(HelloWorldService helloWorldService) {
        System.out.println("I am coming here");
        this.helloWorldService = helloWorldService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = helloWorldService.getMessage();
        resp.setContentLength(msg.length());
        resp.getWriter().print(msg);
    }
}
