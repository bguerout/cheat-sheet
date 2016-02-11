package com.sample.jetty.utils;

import org.eclipse.jetty.maven.plugin.JettyWebAppContext;
import org.eclipse.jetty.maven.plugin.ServerSupport;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.junit.runner.Description;

import java.io.File;


public class WebServer extends AbstractRule {

    private Server server;

    public WebServer(int port) {

        try {
            server = new Server(port);
            configureServer(server, createWebAppContext());
        } catch (Exception e) {
            throw new RuntimeException("Unable to create Jetty web app context", e);
        }
    }

    private void configureServer(Server server, JettyWebAppContext context) {
        Resource.setDefaultUseCaches(false);
        ServerSupport.configureDefaultConfigurationClasses(server);
        server.setStopAtShutdown(true);
        server.setHandler(context);
    }

    private JettyWebAppContext createWebAppContext() throws Exception {

        JettyWebAppContext context = new JettyWebAppContext();
        context.setParentLoaderPriority(true);
        context.setContextPath("/");
        context.setJettyEnvXml("./src/test/resources/jetty-env.xml");
        context.setTempDirectory(new File("./target/tmp"));
        context.setClasses(new File("./target/classes"));
        context.setResourceBase("./src/main/webapp/");
        context.setWar("./src/main/webapp/");
        context.setClassLoader(new WebAppClassLoader(context));
        context.setInitParameter("resteasy.injector.factory", "org.jboss.resteasy.cdi.CdiInjectorFactory");

        return context;
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
