package com.munsun.monitoring_service.backend.controllers.advice;

import com.munsun.monitoring_service.commons.dto.out.ErrorDtoOut;

public class ErrorController {
    public ErrorDtoOut handlerExceptions(Exception e) {
        return new ErrorDtoOut(e.getMessage());
    }
}
