package modules;

import com.google.inject.AbstractModule;
import services.HelloWorldService;

public class AppDefaultModule extends AbstractModule {

    public AppDefaultModule() {
        System.out.println("I am being called");
    }

    protected void configure() {
        bind(HelloWorldService.class);
    }
}
