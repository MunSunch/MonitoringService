package com.munsun.utils.logger.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.munsun.utils.logger")
@EnableAspectJAutoProxy
public class SpringContextLoggerConfig {}