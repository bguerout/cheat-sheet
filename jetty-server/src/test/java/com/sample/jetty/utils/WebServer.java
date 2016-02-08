package com.sample.jetty.utils;

import org.eclipse.jetty.maven.plugin.JettyWebAppContext;
import org.eclipse.jetty.server.Server;
import org.junit.runner.Description;


public class WebServer extends AbstractRule {

    private Server server;

    public WebServer(int port) {
        server = new Server(port);
        server.setStopAtShutdown(true);

        //Enable parsing of jndi-related parts of web.xml and jetty-env.xml
        org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");


        try {
            JettyWebAppContext webAppContext = new JettyWebAppContext();
            webAppContext.setJettyEnvXml("./src/test/resources/jetty-env.xml");
            webAppContext.setContextPath("/app");
            webAppContext.setResourceBase("./src/main/webapp");
            webAppContext.setWar("./src/main/webapp");
            webAppContext.setClassLoader(Server.class.getClassLoader());
            server.setHandler(webAppContext);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create Jetty web app context", e);
        }
    }

    @Override
    protected void before(Description description) throws Throwable {
        server.start();
    }

    @Override
    protected void after(Description description) throws Throwable {
        server.stop();
    }

}
