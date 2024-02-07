package com.munsun.monitoring_service.commons.utils;

public interface PropertyService {
    String getProperty(String nameProperty);
    void setProperty(String nameProperty, String newValue);
}
