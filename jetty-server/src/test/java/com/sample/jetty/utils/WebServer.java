package com.sample.jetty.utils;

import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.jetty.jndi.factories.MailSessionReference;
import org.eclipse.jetty.maven.plugin.JettyWebAppContext;
import org.eclipse.jetty.maven.plugin.ServerSupport;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.junit.runner.Description;

import javax.naming.NamingException;
import java.io.File;
import java.util.Properties;


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

    private void configureServer(Server server, JettyWebAppContext context) throws NamingException {
        Resource.setDefaultUseCaches(false);
        ServerSupport.configureDefaultConfigurationClasses(server);
        server.setStopAtShutdown(true);
        server.setHandler(context);

    }

    private org.eclipse.jetty.plus.jndi.Resource registerDataSource(JettyWebAppContext context) throws NamingException {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        org.eclipse.jetty.plus.jndi.Resource resource = new org.eclipse.jetty.plus.jndi.Resource(context, "jdbc/testDS", ds);
        return resource;
    }

    private void registerMail(JettyWebAppContext context) throws NamingException {
        MailSessionReference mailSessionReference = new MailSessionReference();
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", "localhost");
        properties.put("mail.smtp.port", 3025);
        properties.put("mail.debug", true);
        mailSessionReference.setProperties(properties);

        new org.eclipse.jetty.plus.jndi.Resource(context, "jdbc/testDS", mailSessionReference);
    }

    private JettyWebAppContext createWebAppContext() throws Exception {

        JettyWebAppContext context = new JettyWebAppContext();
        context.setParentLoaderPriority(true);
        context.setContextPath("/");
        //context.setJettyEnvXml("./src/test/resources/jetty-env.xml");
        context.setTempDirectory(new File("./target/tmp"));
        context.setClasses(new File("./target/classes"));
        context.setResourceBase("./src/main/webapp/");
        context.setWar("./src/main/webapp/");
        context.setClassLoader(new WebAppClassLoader(context));
        context.setInitParameter("resteasy.injector.factory", "org.jboss.resteasy.cdi.CdiInjectorFactory");


        registerDataSource(context);

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
