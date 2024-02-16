package com.munsun.monitoring_service.backend.configurations.root;

import com.munsun.monitoring_service.commons.configurations.SpringContextCommonsConfig;
import com.munsun.utils.logger.configurations.SpringContextLoggerConfig;
import org.springframework.context.annotation.*;

@Configuration
@Import(value = {SpringContextCommonsConfig.class, SpringContextLoggerConfig.class})
@ComponentScan(basePackages = "com.munsun.monitoring_service.backend")
@PropertySource(value = "classpath:application.properties")
public class SpringContextBackendConfig {}