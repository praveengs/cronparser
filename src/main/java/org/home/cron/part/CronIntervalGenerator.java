package org.home.cron.part;

import org.home.cron.CronEntity;

import java.util.List;

public abstract class CronIntervalGenerator {
    private CronIntervalGenerator nextIntervalGenerator;

    protected abstract boolean accepts(final String cronPartExpression);

    protected abstract List<String> calculateCronParts(final CronEntity cronEntity, final String cronPartExpression);

    public List<String> generateCronIntervals(final CronEntity cronEntity, final String cronPartExpression) {
        if (this.accepts(cronPartExpression)) {
            return calculateCronParts(cronEntity, cronPartExpression);
        } else if (nextIntervalGenerator != null) {
            return nextIntervalGenerator.generateCronIntervals(cronEntity, cronPartExpression);
        } else {
            throw new UnsupportedOperationException(String.format("This cron %s is not supported yet for the cron part %s", cronPartExpression, cronEntity));
        }
    }

    public CronIntervalGenerator add(CronIntervalGenerator cronIntervalGenerator) {
        this.nextIntervalGenerator = cronIntervalGenerator;
        return cronIntervalGenerator;
    }
}
