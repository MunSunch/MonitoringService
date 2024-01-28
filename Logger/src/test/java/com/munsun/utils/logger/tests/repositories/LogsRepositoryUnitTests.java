package com.munsun.utils.logger.tests.repositories;

import com.munsun.utils.logger.enums.LevelLog;
import com.munsun.utils.logger.model.Log;
import com.munsun.utils.logger.repositories.LogsRepository;
import com.munsun.utils.logger.repositories.impl.LogsRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class LogsRepositoryUnitTests {
    private LogsRepository repository = new LogsRepositoryImpl();

    @Test
    public void testSaveNewLog() {
        var newLog = new Log(new Date(),
                            LevelLog.INFO,
                            "com.munsun.app.Start",
                            "start container");
        var expected = new Log();
            expected.setId(0L);
            expected.setDate(newLog.getDate());
            expected.setLevel(newLog.getLevel());
            expected.setMessage(newLog.getMessage());
            expected.setNameClass(newLog.getNameClass());

        var actual = repository.save(newLog);

        assertThat(actual)
                .isEqualToComparingOnlyGivenFields(expected);
    }
}
