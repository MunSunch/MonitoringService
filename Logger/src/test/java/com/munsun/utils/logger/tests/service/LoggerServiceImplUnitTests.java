package com.munsun.utils.logger.tests.service;

import com.munsun.utils.logger.enums.LevelLog;
import com.munsun.utils.logger.model.Log;
import com.munsun.utils.logger.repositories.impl.LogsRepositoryImpl;
import com.munsun.utils.logger.service.LoggerService;
import com.munsun.utils.logger.service.impl.LoggerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggerServiceImplUnitTests {
    private LoggerService service = new LoggerServiceImpl(LoggerServiceImplUnitTests.class, new LogsRepositoryImpl());

    @DisplayName("Save the debug log")
    @Test
    public void testAddDebugLog() {
        String expectedMessage = "test debug message";

        service.debug(expectedMessage);
        var actual = service.getAllLogs().get(0);

        assertThat(actual)
                .isNotNull()
                .extracting(Log::getLevel, Log::getMessage)
                    .containsExactly(LevelLog.DEBUG, expectedMessage);
    }

    @DisplayName("Save the info log")
    @Test
    public void testAddInfoLog() {
        String expectedMessage = "test info message";

        service.info(expectedMessage);
        var actual = service.getAllLogs().get(0);

        assertThat(actual)
                .isNotNull()
                .extracting(Log::getLevel, Log::getMessage)
                    .containsExactly(LevelLog.INFO, expectedMessage);
    }

    @DisplayName("Save the warning log")
    @Test
    public void testAddWarningLog() {
        String expectedMessage = "test info message";

        service.warning(expectedMessage);
        var actual = service.getAllLogs().get(0);

        assertThat(actual)
                .isNotNull()
                .extracting(Log::getLevel, Log::getMessage)
                    .containsExactly(LevelLog.WARN, expectedMessage);
    }

    @DisplayName("Save the info log")
    @Test
    public void testAddErrorLog() {
        String expectedMessage = "test info message";

        service.error(expectedMessage);
        var actual = service.getAllLogs().get(0);

        assertThat(actual)
                .isNotNull()
                .extracting(Log::getLevel, Log::getMessage)
                .containsExactly(LevelLog.ERROR, expectedMessage);
    }

    @DisplayName("Test get all logs")
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5})
    public void testGetAllLogs(int countRepeat) {
        String testMessage = "test";

        for (int i = 0; i < countRepeat; i++) {
            service.warning(testMessage);
        }
        var actual = service.getAllLogs();

        assertThat(actual)
                .hasSize(countRepeat)
                .allMatch(x -> x.getMessage().equals(testMessage))
                .allMatch(x -> x.getLevel() == LevelLog.WARN);
    }
}