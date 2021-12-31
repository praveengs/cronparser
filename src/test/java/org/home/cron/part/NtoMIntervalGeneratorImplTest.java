package org.home.cron.part;

import org.home.cron.CronEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NtoMIntervalGeneratorImplTest {
    private NtoMIntervalGeneratorImpl cronIntervalGenerator;

    @BeforeEach
    void setUp() {
        cronIntervalGenerator = new NtoMIntervalGeneratorImpl();
    }

    @MethodSource
    @ParameterizedTest(name = "{0}")
    void testCronGenerator(String description, List<String> expectedValue, CronEntity cronEntity, String cronPartExpression, boolean isSupported) {
        if (isSupported) {
            assertEquals(expectedValue, cronIntervalGenerator.generateCronIntervals(cronEntity, cronPartExpression));
        } else {
            assertThrows(UnsupportedOperationException.class, () -> cronIntervalGenerator.generateCronIntervals(cronEntity, cronPartExpression));
        }
    }

    private static Stream<Arguments> testCronGenerator() {
        return Stream.of(
                Arguments.of("Happy path cron expression",
                        List.of("1", "2", "3", "4", "5", "6"),
                        CronEntity.DAY_OF_WEEK,
                        "1-6",
                        true),
                Arguments.of("A non supported cron expression is rejected",
                        List.of(),
                        CronEntity.DAY_OF_WEEK,
                        "5",
                        false),
                Arguments.of("Test the max values",
                        List.of("1", "2", "3", "4", "5", "6", "7"),
                        CronEntity.DAY_OF_WEEK,
                        "1-10",
                        true),
                Arguments.of("Test the min values",
                        List.of("1", "2", "3", "4", "5"),
                        CronEntity.DAY_OF_WEEK,
                        "0-5",
                        true)
        );
    }
}
