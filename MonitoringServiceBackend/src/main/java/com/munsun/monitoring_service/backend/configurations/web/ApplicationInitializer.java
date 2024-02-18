package com.munsun.monitoring_service.backend.configurations.web;

import com.munsun.monitoring_service.backend.configurations.root.SpringContextBackendConfig;
import com.munsun.monitoring_service.backend.configurations.root.SecurityConfig;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@Import({SecurityConfig.class})
public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        var rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringContextBackendConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));

        Servlet dispatcher = new DispatcherServlet(rootContext);
        var registration = servletContext.addServlet("app", dispatcher);
        registration.setLoadOnStartup(1);
        registration.addMapping("/*");

        FilterRegistration.Dynamic securityFilter = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        securityFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
