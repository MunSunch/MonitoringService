package com.munsun.utils.logger.tests.repositories;

import com.munsun.utils.logger.enums.LevelLog;
import com.munsun.utils.logger.model.Log;
import com.munsun.utils.logger.repositories.LogsRepository;
import com.munsun.utils.logger.repositories.impl.LogsRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class LogsRepositoryUnitTests {
    private LogsRepository repository = new LogsRepositoryImpl();

    @DisplayName("Positive test save the log")
    @Test
    public void testSaveNewLog() {
        var newLog = new Log(null,
                            new Date(),
                            LevelLog.INFO,
                            "com.munsun.app.Start",
                            "start container");
        var expected = Log.builder()
                                .id(0L)
                                .date(newLog.getDate())
                                .level(newLog.getLevel())
                                .message(newLog.getMessage())
                                .nameClass(newLog.getNameClass())
                            .build();

        repository.save(newLog);
        var actual = repository.getAllLogs().get(0);

        assertThat(actual)
                .extracting(Log::getId, Log::getMessage, Log::getLevel, Log::getNameClass)
                .containsExactly(0L, "start container", LevelLog.INFO, "com.munsun.app.Start");
    }
}