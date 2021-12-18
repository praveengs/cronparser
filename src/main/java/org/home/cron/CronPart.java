package org.home.cron;

public enum CronPart {
    MINUTE(0, 59, "minute"),
    HOUR(0, 24, "hour"),
    DAY_OF_MONTH(1, 31, "day of month"),
    MONTH(1, 12, "month"),
    DAY_OF_WEEK(1, 7, "day of week"),
    COMMAND("command");

    private int low;
    private int high;
    private final String name;

    CronPart(int low, int high, String name) {

        this.low = low;
        this.high = high;
        this.name = name;
    }

    CronPart(String name) {
        this.name = name;
    }

    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }

    public String getName() {
        return name;
    }
}
