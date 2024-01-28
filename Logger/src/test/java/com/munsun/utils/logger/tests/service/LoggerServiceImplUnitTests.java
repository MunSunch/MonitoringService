package com.munsun.utils.logger.tests.service;

import com.munsun.utils.logger.enums.LevelLog;
import com.munsun.utils.logger.repositories.impl.LogsRepositoryImpl;
import com.munsun.utils.logger.service.LoggerService;
import com.munsun.utils.logger.service.impl.LoggerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggerServiceImplUnitTests {
    private LoggerService service = new LoggerServiceImpl(LoggerServiceImplUnitTests.class, new LogsRepositoryImpl());

    @Test
    public void testAddDebugLog() {
        service.debug("test debug message");

        var actual = service.getAllLogs().get(0);
        assertThat(actual.getLevel()).isEqualTo(LevelLog.DEBUG);
    }

    @Test
    public void testAddInfoLog() {
        service.info("test info message");

        var actual = service.getAllLogs().get(0);
        assertThat(actual.getLevel()).isEqualTo(LevelLog.INFO);
    }

    @Test
    public void testAddWarningLog() {
        service.warning("test warning message");

        var actual = service.getAllLogs().get(0);
        assertThat(actual.getLevel()).isEqualTo(LevelLog.WARN);
    }

    @Test
    public void testAddErrorLog() {
        service.error("test error message");

        var actual = service.getAllLogs().get(0);
        assertThat(actual.getLevel()).isEqualTo(LevelLog.ERROR);
    }


    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5})
    public void testGetAllLogs(int countRepeat) {
        for (int i = 0; i < countRepeat; i++) {
            service.warning("test");
        }
        var actual = service.getAllLogs().size();
        assertThat(actual).isEqualTo(countRepeat);
    }
}











