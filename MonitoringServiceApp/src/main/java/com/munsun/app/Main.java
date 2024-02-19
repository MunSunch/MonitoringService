package com.munsun.app;

import com.munsun.monitoring_service.backend.configurations.web.ApplicationInitializer;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.ApplicationContextFacade;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, LifecycleException, ServletException {
        var tomcat = new Tomcat();
        var baseDir = Files.createTempDirectory("tomcat");
        baseDir.toFile().deleteOnExit();
        tomcat.setBaseDir(baseDir.toAbsolutePath().toString());

        final var connector = new Connector();
        connector.setPort(9999);
        tomcat.setConnector(connector);

        tomcat.getHost().setAppBase(".");
        var context = tomcat.addContext("/", ".");
        context.addServletContainerInitializer(new SpringServletContainerInitializer(), Set.of(ApplicationInitializer.class));

        tomcat.start();
        tomcat.getServer().await();
    }
}