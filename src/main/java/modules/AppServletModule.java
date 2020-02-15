package modules;

import com.google.inject.servlet.ServletModule;
import servlets.HelloWorldServlet;

public class AppServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(HelloWorldServlet.class);

        serve("/hello").with(HelloWorldServlet.class);
    }
}
