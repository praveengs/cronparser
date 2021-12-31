package org.home.cron.part;

import org.home.cron.CronEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EchoCronIntervalGeneratorImplTest {
    private EchoCronIntervalGeneratorImpl cronIntervalGenerator;

    @BeforeEach
    void setUp() {
        cronIntervalGenerator = new EchoCronIntervalGeneratorImpl();
    }

    @MethodSource
    @ParameterizedTest(name = "{0}")
    void testCronGenerator(String description, List<String> expectedValue, CronEntity cronEntity, String cronPartExpression) {
        assertEquals(expectedValue, cronIntervalGenerator.generateCronIntervals(cronEntity, cronPartExpression));
    }

    private static Stream<Arguments> testCronGenerator() {
        return Stream.of(
                Arguments.of("Happy path cron expression",
                        List.of("5"),
                        CronEntity.HOUR,
                        "5"),
                Arguments.of("Test the max values",
                        List.of(),
                        CronEntity.HOUR,
                        "25"),
                Arguments.of("Test the min values",
                        List.of(),
                        CronEntity.HOUR,
                        "-1")
        );
    }
}