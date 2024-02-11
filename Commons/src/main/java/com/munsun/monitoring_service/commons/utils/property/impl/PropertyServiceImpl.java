package com.munsun.monitoring_service.commons.utils.property.impl;

import com.munsun.monitoring_service.commons.utils.property.PropertyService;

import java.io.IOException;
import java.util.Properties;

public class PropertyServiceImpl implements PropertyService {
    private Properties properties;
    private final Class<?> tClass;
    private static final String DEFAULT_PATH_TO_RESOURCES = "/app.properties";

    public PropertyServiceImpl(Class<?> tClass) {
        this.properties = new Properties();
        this.tClass = tClass;
        loadResources(tClass, DEFAULT_PATH_TO_RESOURCES);
    }

    private void loadResources(Class<?> tClass, String pathToResources) {
        try {
            properties.load(tClass.getResourceAsStream(pathToResources));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getProperty(String nameProperty) {
        return properties.getProperty(nameProperty);
    }

    @Override
    public void setProperty(String nameProperty, String newValue) {
        properties.setProperty(nameProperty, newValue);
    }
}
