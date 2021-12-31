package org.home.cron;

import org.home.cron.part.*;

import java.util.*;
import java.util.stream.Collectors;

public class CronParser {

    private final CronIntervalGenerator cronIntervalGeneratorChain;

    public CronParser(final CronIntervalGenerator cronIntervalGeneratorChain) {
        this.cronIntervalGeneratorChain = cronIntervalGeneratorChain;
    }

    /**
     * Parse the cron string into different parts and process each part.
     *
     * @param cronString a single line cron string with space separated parts.
     * @return a map with name of the cron part, eg minute, as the key; and the cron intervals as the values.
     */
    public Map<String, List<String>> parseCron(String cronString) {
        Map<String, List<String>> cronMap = new LinkedHashMap<>();
        String[] cronParts = cronString.split(" ");
        cronMap.put(CronEntity.MINUTE.getName(), processCronPart(CronEntity.MINUTE, cronParts[0]));
        cronMap.put(CronEntity.HOUR.getName(), processCronPart(CronEntity.HOUR, cronParts[1]));
        cronMap.put(CronEntity.DAY_OF_MONTH.getName(), processCronPart(CronEntity.DAY_OF_MONTH, cronParts[2]));
        cronMap.put(CronEntity.MONTH.getName(), processCronPart(CronEntity.MONTH, cronParts[3]));
        cronMap.put(CronEntity.DAY_OF_WEEK.getName(), processCronPart(CronEntity.DAY_OF_WEEK, cronParts[4]));
        cronMap.put(CronEntity.COMMAND.getName(), processCronPart(CronEntity.COMMAND, cronParts[5]));
        return cronMap;
    }

    /**
     * Method that accepts a cron string and generates a report of what each cron part is, and the values that the
     * cron expression represents, in a user-friendly manner.
     *
     * @param cronString a single line cron string with space separated parts.
     * @return a multi line string, with each line representing each part of the cron and its values.
     */
    public String cronReport(String cronString) {
        Map<String, List<String>> cronMap = parseCron(cronString);
        return cronMap.entrySet().stream().sequential()
                .map(stringListEntry -> String.format("%1$-14s", stringListEntry.getKey()) + String.join(" ", stringListEntry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Utility method to create the chain of responsibility to parse the cron parts.
     */
    static CronIntervalGenerator createCronIntervalGeneratorChain() {
        CronIntervalGenerator cronIntervalGeneratorChain = new EveryNCronIntervalGeneratorImpl();
        cronIntervalGeneratorChain.add(new ExactCronIntervalGeneratorImpl())
                .add(new NtoMIntervalGeneratorImpl())
                .add(new EveryIntervalGeneratorImpl())
                .add(new EchoCronIntervalGeneratorImpl());

        return cronIntervalGeneratorChain;
    }

    private List<String> processCronPart(CronEntity CRON_PART, String cronExpression) {
        List<String> parts = new LinkedList<>();
        if (CRON_PART == CronEntity.COMMAND) {
            parts.add(cronExpression);
        } else {
            parts.addAll(cronIntervalGeneratorChain.generateCronIntervals(CRON_PART, cronExpression));
        }
        return parts;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.printf("Please provide a cron expression as the first and only argument. Current args: %s", Arrays.asList(args));
        } else {
            CronParser cronParser = new CronParser(createCronIntervalGeneratorChain());
            System.out.printf("The parsed cron is \n%s", cronParser.cronReport(args[0]));
        }
    }


}
