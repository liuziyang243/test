package com.crscd.framework.logtest;

import com.crscd.framework.util.log.ThrottledSlf4jLogger;
import com.crscd.framework.util.time.ClockUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author lzy
 * Date: 2017/7/20
 * Time: 9:51
 */
public class ThrottledSlf4jLoggerTest {
    @Test
    public void test() {
        try {
            ClockUtil.DummyClock clock = ClockUtil.useDummyClock();
            Logger realLogger = LoggerFactory.getLogger(ThrottledSlf4jLoggerTest.class);
            LogbackListAppender appender = LogbackListAppender.create(ThrottledSlf4jLoggerTest.class);
            // 间隔10毫秒
            ThrottledSlf4jLogger logger = new ThrottledSlf4jLogger(realLogger, 10, TimeUnit.MILLISECONDS);

            logger.warn("haha");
            assertThat(appender.getLogsCount()).isEqualTo(1);

            // still 1
            logger.warn("haha {}", 1);
            assertThat(appender.getLogsCount()).isEqualTo(1);

            // still 1
            logger.error("haha {}", 1);
            assertThat(appender.getLogsCount()).isEqualTo(1);
            // still 1
            clock.increaseTime(5);
            logger.error("haha, {} {} {}", 1, 2, 3);
            assertThat(appender.getLogsCount()).isEqualTo(1);

            // 10ms pass
            clock.increaseTime(5);
            logger.warn("haha");
            assertThat(appender.getLogsCount()).isEqualTo(2);

            // still 2
            logger.warn("haha {} {} {}", 1, 2, 3);
            assertThat(appender.getLogsCount()).isEqualTo(2);

            // still 2
            clock.increaseTime(7);
            logger.warn("haha");
            assertThat(appender.getLogsCount()).isEqualTo(2);

        } finally {
            ClockUtil.useDefaultClock();
        }

    }
}
