package com.munsun.monitoring_service.commons.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.munsun.monitoring_service.commons")
@PropertySource("classpath:app.properties")
public class SpringContextCommonsConfig {}