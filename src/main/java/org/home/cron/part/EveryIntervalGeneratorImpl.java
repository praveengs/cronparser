package org.home.cron.part;

import org.home.cron.CronEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * This part handles the expression *, where we have to consider every part between the
 * low and the high of the CRONPART.
 */
public class EveryIntervalGeneratorImpl extends CronIntervalGenerator {
    @Override
    public boolean accepts(String cronPartExpression) {
        return cronPartExpression.equals("*");
    }

    @Override
    public List<String> calculateCronParts(final CronEntity cronEntity, final String cronPartExpression) {
        List<String> parts = new LinkedList<>();
        int interval = 1;
        int cronPartToAdd = cronEntity.getLow();
        while (cronPartToAdd <= cronEntity.getHigh()) {
            parts.add(String.valueOf(cronPartToAdd));
            cronPartToAdd += interval;
        }
        return parts;
    }
}
