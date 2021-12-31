package org.home.cron.part;

import org.home.cron.CronEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * This will handle a cron expression like 1-5, where we have to use every
 * part between 1 and 5, i.e. 1,2,3,4,5.
 */
public class NtoMIntervalGeneratorImpl extends CronIntervalGenerator {
    @Override
    public boolean accepts(String cronPartExpression) {
        return cronPartExpression.contains("-") && cronPartExpression.indexOf("-") > 0;
    }

    @Override
    public List<String> calculateCronParts(CronEntity cronEntity, String cronPartExpression) {
        String[] cronStringParts = cronPartExpression.split("-");
        int start = cronStringParts[0].equals("*") ? cronEntity.getLow() : Integer.parseInt(cronStringParts[0]);
        int interval = 1;
        int cronPartToAdd = start;
        int max = Integer.parseInt(cronStringParts[1]);
        List<String> parts = new LinkedList<>();
        while (cronPartToAdd <= cronEntity.getHigh() && cronPartToAdd <= max) {
            if (cronPartToAdd >= cronEntity.getLow()) {
                parts.add(String.valueOf(cronPartToAdd));
            }
            cronPartToAdd += interval;
        }
        return parts;
    }
}
