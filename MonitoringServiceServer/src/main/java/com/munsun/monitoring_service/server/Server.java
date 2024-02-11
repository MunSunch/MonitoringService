package com.munsun.monitoring_service.server;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
public class Server {
    private final Servlet dispatcherServlet;
    private final Filter delegateFilter;

    public void start(int port) throws IOException, LifecycleException {
        var tomcat = new Tomcat();
        var baseDir = Files.createTempDirectory("tomcat");
        baseDir.toFile().deleteOnExit();
        tomcat.setBaseDir(baseDir.toAbsolutePath().toString());

        String pathContext = "/";
        String docBase = ".";
        String nameServlet = "dispatcher";
        var context = tomcat.addContext(pathContext, docBase);
        tomcat.addServlet(pathContext, nameServlet, dispatcherServlet);
        context.addServletMappingDecoded(pathContext, nameServlet);

        String nameFilter = "SecurityDelegateFilter";
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName(nameFilter);
        filterDef.setFilter(delegateFilter);
        context.addFilterDef(filterDef);

        FilterMap myFilterMap = new FilterMap();
        myFilterMap.setFilterName(nameFilter);
        myFilterMap.addURLPattern("/*");
        context.addFilterMap(myFilterMap);

        var connector = new Connector();
        connector.setPort(port);
        tomcat.setConnector(connector);
        tomcat.getHost().setAppBase(docBase);

        tomcat.start();
        tomcat.getServer().await();
    }
}
