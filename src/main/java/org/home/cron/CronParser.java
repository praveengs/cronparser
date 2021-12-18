package org.home.cron;

import java.util.*;
import java.util.stream.Collectors;

public class CronParser {

    public Map<String, List<String>> parseCron(String cronString) {
        Map<String, List<String>> cronMap = new LinkedHashMap<>();
        String[] cronParts = cronString.split(" ");
        cronMap.put(CronPart.MINUTE.getName(), processCronPart(CronPart.MINUTE, cronParts[0]));
        cronMap.put(CronPart.HOUR.getName(), processCronPart(CronPart.HOUR, cronParts[1]));
        cronMap.put(CronPart.DAY_OF_MONTH.getName(), processCronPart(CronPart.DAY_OF_MONTH, cronParts[2]));
        cronMap.put(CronPart.MONTH.getName(), processCronPart(CronPart.MONTH, cronParts[3]));
        cronMap.put(CronPart.DAY_OF_WEEK.getName(), processCronPart(CronPart.DAY_OF_WEEK, cronParts[4]));
        cronMap.put(CronPart.COMMAND.getName(), processCronPart(CronPart.COMMAND, cronParts[5]));
        return cronMap;
    }

    public String cronReport(String cronString) {
        Map<String, List<String>> cronMap = parseCron(cronString);
        return cronMap.entrySet().stream().sequential()
                .map(stringListEntry -> String.format("%1$-14s",stringListEntry.getKey()) + String.join(" ", stringListEntry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    private List<String> processCronPart(CronPart CRON_PART, String cronExpression) {
        List<String> parts = new LinkedList<>();
        if (CRON_PART == CronPart.COMMAND) {
            parts.add(cronExpression);
        } else if (cronExpression.contains("/")) {
            handleEveryNInterval(CRON_PART.getHigh(), CRON_PART.getLow(), parts, cronExpression.split("/"));
        } else if (cronExpression.contains(",")) {
            handleExactInterval(CRON_PART.getHigh(), CRON_PART.getLow(), parts, cronExpression.split(","));
        } else if (cronExpression.contains("-")) {
            handleNtoMInterval(CRON_PART.getHigh(), CRON_PART.getLow(), parts, cronExpression.split("-"));
        } else if (cronExpression.equals("*")) {
            handleEveryInterval(CRON_PART.getHigh(), CRON_PART.getLow(), parts);
        } else {
            handleEntry(CRON_PART.getHigh(), CRON_PART.getLow(), parts, cronExpression);
        }
        return parts;
    }

    private void handleEntry(int high, int low, List<String> parts, String cronExpression) {
        int cronPartInt = Integer.parseInt(cronExpression);
        if (cronPartInt >= low && cronPartInt <= high) {
            parts.add(cronExpression);
        }
    }

    private void handleEveryInterval(int high, int low, List<String> parts) {
        int interval = 1;
        int cronPartToAdd = low;
        while (cronPartToAdd <= high) {
            parts.add(String.valueOf(cronPartToAdd));
            cronPartToAdd += interval;
        }
    }

    private void handleNtoMInterval(int high, int low, List<String> parts, String[] cronPart) {
        int start = cronPart[0].equals("*") ? low : Integer.parseInt(cronPart[0]);
        int interval = 1;
        int cronPartToAdd = start;
        int max = Integer.parseInt(cronPart[1]);
        while (cronPartToAdd <= high && cronPartToAdd <= max) {
            parts.add(String.valueOf(cronPartToAdd));
            cronPartToAdd += interval;
        }
    }

    private void handleExactInterval(int high, int low, List<String> parts, String[] cronPart) {
        for (String cronPartToAdd : cronPart) {
            int cronPartInt = Integer.parseInt(cronPartToAdd);
            if (cronPartInt >= low && cronPartInt <= high) {
                parts.add(cronPartToAdd);
            }
        }
    }

    private void handleEveryNInterval(int high, int low, List<String> parts, String[] cronPart) {
        int start = cronPart[0].equals("*") ? low : Integer.parseInt(cronPart[0]);
        int interval = Integer.parseInt(cronPart[1]);
        int cronPartToAdd = start;
        while (cronPartToAdd <= high) {
            parts.add(String.valueOf(cronPartToAdd));
            cronPartToAdd += interval;
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.printf("Please provide a cron expression as the first and only argument. Current args: %s", Arrays.asList(args));
        } else {
            CronParser cronParser = new CronParser();
            System.out.printf("The parsed cron is \n%s", cronParser.cronReport(args[0]));
        }
    }
}
