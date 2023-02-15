package cron;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CronUtils {

    public final static Integer CRON_APP_INPUT_ARGUMENT_SIZE = 6;

    public final static String FIELD_SPACE_SEPARATOR = " ";
    public final static String FIELD_COMMA_SEPARATOR = ",";
    public final static String FIELD_MINUS_SEPARATOR = "-";

    public final static String CRON_ASTERISK = "*";
    public final static String CRON_ASTERISK_WITH_SLASH = "*/";
    public final static String CRON_HYPHEN = "-";
    public final static String CRON_COMMA = ",";
    public final static String CRON_FORWARD_SLASH = "/";
    public final static String CRON_ONLY_DIGIT = "";

    public static String removeAllDigits(String str) {
        return Arrays.asList(str.split(""))
                .stream()
                .filter(ch -> !ch.matches("\\d"))
                .distinct()
                .collect(Collectors.joining());
    }
}
