import com.google.inject.servlet.GuiceFilter;
import configuration.AppServletContextListener;
import filters.FbAuthenticationFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addFilter(FbAuthenticationFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        servletContextHandler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        servletContextHandler.addEventListener(new AppServletContextListener());
        server.setHandler(servletContextHandler);
        server.start();
        server.join();
    }
}
