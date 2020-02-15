package configuration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import modules.AppDefaultModule;
import modules.AppServletModule;


public class AppServletContextListener extends GuiceServletContextListener {

    protected Injector getInjector() {
        System.out.println("Injector being created");
        return Guice.createInjector(new AppDefaultModule(), new AppServletModule());
    }
}
