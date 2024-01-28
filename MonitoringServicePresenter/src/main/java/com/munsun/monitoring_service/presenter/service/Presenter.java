package com.munsun.monitoring_service.presenter.service;

import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.frontend.in.service.View;

/**
 *  An abstraction for coordinating the actions of View and Service, which are represented by the corresponding interfaces
 */
public abstract class Presenter {
    private View view;
    private MonitoringService service;

    public Presenter(View view, MonitoringService service) {
        this.view = view;
        this.service = service;
    }

    /**
     * The representative launches
     */
    public abstract void start();

    /**
     * Return the view object that is responsible for user interaction
     * @return View
     */
    public View getView() {
        return view;
    }

    /**
     * Return the Service object that is responsible for processing and storing data
     * @return MonitoringService
     */
    public MonitoringService getService() {
        return service;
    }
}