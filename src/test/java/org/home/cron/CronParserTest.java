package org.home.cron;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CronParserTest {

    private CronParser cronParser;

    @BeforeEach
    void setUp() {
        cronParser = new CronParser();
    }

    @Test
    void parseCronSimple() {
        Map<String, List<String>> expectedValues = new LinkedHashMap<>();
        expectedValues.put("minute", List.of("0", "15", "30", "45"));
        expectedValues.put("hour", List.of("0"));
        expectedValues.put("day of month", List.of("1", "15"));
        expectedValues.put("month", List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
        expectedValues.put("day of week", List.of("1", "2", "3", "4", "5"));
        expectedValues.put("command", List.of("/usr/bin/find"));
        assertEquals(expectedValues, cronParser.parseCron("*/15 0 1,15 * 1-5 /usr/bin/find"));
    }


    @Test
    void cronReport() {
        assertEquals("minute        0 15 30 45\n" +
                "hour          0\n" +
                "day of month  1 15\n" +
                "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                "day of week   1 2 3 4 5\n" +
                "command       /usr/bin/find", cronParser.cronReport("*/15 0 1,15 * 1-5 /usr/bin/find"));
    }
}