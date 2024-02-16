package com.munsun.monitoring_service.backend;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws LifecycleException, IOException {
        var tomcat = new Tomcat();
        var baseDir = Files.createTempDirectory("tomcat");
        baseDir.toFile().deleteOnExit();
        tomcat.setBaseDir(baseDir.toAbsolutePath().toString());

        final var connector = new Connector();
        connector.setPort(9999);
        tomcat.setConnector(connector);

        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp("/", new File("MonitoringServiceBackend/target/MonitoringServiceBackend-1.0-SNAPSHOT/WEB-INF").getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}
