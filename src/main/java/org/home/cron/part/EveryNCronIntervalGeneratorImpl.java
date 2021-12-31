package org.home.cron.part;

import org.home.cron.CronEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * This part handles the m by n part, where we repeat the program every n intervals starting at m.
 * Say for e.g. 1/15 would mean the program would run every 1, 16th, 31st, ... units of time till the max.
 */
public class EveryNCronIntervalGeneratorImpl extends CronIntervalGenerator {

    public static final String CRON_PATTERN = "/";

    @Override
    public boolean accepts(String cronPartExpression) {
        return cronPartExpression.contains(CRON_PATTERN);
    }

    @Override
    public List<String> calculateCronParts(CronEntity cronEntity, String cronPartExpression) {
        String[] cronStringParts = cronPartExpression.split(CRON_PATTERN);
        int start = cronStringParts[0].equals("*") ? cronEntity.getLow() : Integer.parseInt(cronStringParts[0]);
        int interval = Integer.parseInt(cronStringParts[1]);
        int cronPartToAdd = start;
        List<String> parts = new LinkedList<>();
        while (cronPartToAdd <= cronEntity.getHigh()) {
            parts.add(String.valueOf(cronPartToAdd));
            cronPartToAdd += interval;
        }
        return parts;
    }
}
