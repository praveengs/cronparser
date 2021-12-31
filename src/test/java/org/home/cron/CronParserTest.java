package org.home.cron;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CronParserTest {

    private CronParser cronParser;

    @BeforeEach
    void setUp() {
        cronParser = new CronParser(CronParser.createCronIntervalGeneratorChain());
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

    @MethodSource
    @ParameterizedTest(name = "{0}")
    void testCronReport(String description, String expectedValue, String cronExpression) {
        assertEquals(expectedValue, cronParser.cronReport(cronExpression));
    }

    private static Stream<Arguments> testCronReport() {
        return Stream.of(
                Arguments.of("Happy path cron expression",
                        "minute        0 15 30 45\n" +
                                "hour          0\n" +
                                "day of month  1 15\n" +
                                "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                                "day of week   1 2 3 4 5\n" +
                                "command       /usr/bin/find",
                        "*/15 0 1,15 * 1-5 /usr/bin/find"),
                Arguments.of("Test the max values",
                        "minute        0 15 30 45\n" +
                                "hour          \n" +
                                "day of month  1\n" +
                                "month         \n" +
                                "day of week   1 2 3 4 5 6 7\n" +
                                "command       /usr/bin/find",
                        "*/15 25 1,41 13 1-8 /usr/bin/find"),
                Arguments.of("Test the min values",
                        "minute        0 15 30 45\n" +
                                "hour          \n" +
                                "day of month  11\n" +
                                "month         \n" +
                                "day of week   1 2 3 4 5 6 7\n" +
                                "command       /usr/bin/find",
                        "*/15 -1 0,11 0 0-7 /usr/bin/find")
        );
    }
}