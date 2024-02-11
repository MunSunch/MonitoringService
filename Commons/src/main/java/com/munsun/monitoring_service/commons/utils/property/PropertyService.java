package com.munsun.monitoring_service.commons.utils.property;

public interface PropertyService {
    String getProperty(String nameProperty);
    void setProperty(String nameProperty, String newValue);
}
