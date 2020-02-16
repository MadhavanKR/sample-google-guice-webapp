This is a sample web application developed using google juice framework and the application is run on an embedded jetty server

Important concepts:
*) The execution starts with the Main class where we create and configure a "ServletContextHandler" object. 
*) The ServletContextHandler object is a programmatic equivalent of the traditional deployment descriptor "web.xml"
*) We first add the AuthenticationFilter and later the GuiceFilter to the ServletContextHandler object since we want the requests to be processed in that order. That is, we want the requests to be authenticated first and served later.
*) Adding GuiceFilter class as an filter is our way of letting the jetty server know that all the requests matching a certain pattern must be routed to the GuiceFilter, which is the spring's DispatcherServlet equivalent in Guice.
*) Further we go on to add an listener as AppServletContextListener object. This AppServletContextListener class is a subclass of ServletContextListener class which will get called by default before execution of first request.
*) It is important to note that we are creating all our injectors in AppServletContextListener class because that will make sure that all our dependency bindings are successfully done before serving any requests.
*) Another important thing to note is that one of the modules for which injector is created is the "AppServletModule" which is a subclass of ServletModule. The ServletModule is Guice Framework's module that helps in configuring the servlets to service the requests.
*) We can configure the servelts by overriding the configureServlets method on the ServletModule class and GuiceFilter will take care of the mapping.
*) lastly we set this ServletContextHandler object in the Jetty Server object and start the server.

There you go, your sample web app written using google guice framework is ready to serve your requests!
