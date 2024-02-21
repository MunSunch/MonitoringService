package com.munsun.monitoring_service.backend;

import com.munsun.monitoring_service.commons.db.impl.MigrationSystem;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Application {
    private final MigrationSystem migrationSystem;

    @PostConstruct
    public void init() throws Exception {
        migrationSystem.initSchema();
    }
}
