package org.home.cron.part;

import org.home.cron.CronEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * This part handles a cron part like 1,5,10 where we will be running the program
 * at every 1, 5th and 10th unit of the time.
 */
public class ExactCronIntervalGeneratorImpl extends CronIntervalGenerator {
    @Override
    public boolean accepts(String cronPartExpression) {
        return cronPartExpression.contains(",");
    }

    @Override
    public List<String> calculateCronParts(CronEntity cronEntity, String cronPartExpression) {
        String[] cronStringParts = cronPartExpression.split(",");
        List<String> parts = new LinkedList<>();
        for (String cronPartToAdd : cronStringParts) {
            int cronPartInt = Integer.parseInt(cronPartToAdd);
            if (cronPartInt >= cronEntity.getLow() && cronPartInt <= cronEntity.getHigh()) {
                parts.add(cronPartToAdd);
            }
        }
        return parts;
    }
}
