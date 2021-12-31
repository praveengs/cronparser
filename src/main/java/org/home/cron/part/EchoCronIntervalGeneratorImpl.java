package org.home.cron.part;

import org.home.cron.CronEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * This part handles the entry like say 5, where there is no other substitution is to be made
 * and just used as such. So basically echoing back the input.
 */
public class EchoCronIntervalGeneratorImpl extends CronIntervalGenerator {

    @Override
    public boolean accepts(String cronPartExpression) {
        return true;
    }

    @Override
    public List<String> calculateCronParts(final CronEntity cronEntity, final String cronPartExpression) {
        List<String> parts = new LinkedList<>();
        int cronPartInt = Integer.parseInt(cronPartExpression);
        if (cronPartInt >= cronEntity.getLow() && cronPartInt <= cronEntity.getHigh()) {
            parts.add(cronPartExpression);
        }
        return parts;
    }
}
