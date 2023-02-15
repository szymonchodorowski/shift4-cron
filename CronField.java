package cron;

enum CronField {
    MINUTE("minute", 0, 59),
    HOUR("hour", 0, 23),
    DAY_OF_MONTH("day of month", 1, 31),
    MONTH("month", 1, 12),
    DAY_OF_WEEK("day of week", 1, 7),
    COMMAND("command", null, null);

    private final String description;
    private final Integer minRange;
    private final Integer maxRange;

    CronField(String description, Integer minRange, Integer maxRange) {
        this.description = description;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public String getDescription(){
        return description;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }
}
