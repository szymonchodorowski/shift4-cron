package cron;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

import static cron.CronUtils.*;

public class CronApp {

    public static Map<CronField, String> cronFieldMap;

    public static void main(String[] args) {
        CronApp cronApp = new CronApp();
        try {
            cronApp.validateInputData(args);
            cronApp.createCronFieldMap(args);
            cronApp.printData();
        } catch (Exception ex) {
            System.err.println(System.lineSeparator() + ex.getMessage());
        }
    }

    private void validateInputData(String[] args) {
        if(args == null || args.length != CRON_APP_INPUT_ARGUMENT_SIZE){
            throw new IllegalArgumentException("Wrong argument input length");
        }
    }

    private void createCronFieldMap(String[] args) {
        cronFieldMap = new TreeMap<>();
        cronFieldMap.put(CronField.MINUTE, args[0]);
        cronFieldMap.put(CronField.HOUR, args[1]);
        cronFieldMap.put(CronField.DAY_OF_MONTH, args[2]);
        cronFieldMap.put(CronField.MONTH, args[3]);
        cronFieldMap.put(CronField.DAY_OF_WEEK, args[4]);
        cronFieldMap.put(CronField.COMMAND, args[5]);
    }

    private void printData() {
        for (var entry : cronFieldMap.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();

            System.out.print(key.getDescription() + FIELD_SPACE_SEPARATOR);
            if(key.equals(CronField.COMMAND)){
                System.out.print(value);
            } else {
                switch(CronUtils.removeAllDigits(value)){
                    case CRON_ASTERISK -> printWholeRange(key);
                    case CRON_ASTERISK_WITH_SLASH -> printModuloRange(key, value);
                    case CRON_ONLY_DIGIT -> printResult(value);
                    case CRON_COMMA -> printNumbers(value);
                    case CRON_HYPHEN -> printNumbersFromRange(key, value);
                    default -> throw new UnsupportedOperationException("Not supported operation");
                }
                System.out.print(System.lineSeparator());
            }
        }
    }

    private void printWholeRange(CronField key) {
        IntStream.rangeClosed(key.getMinRange(), key.getMaxRange())
                .forEach(result -> printResult(result));
    }

    private void printModuloRange(CronField key, String value) {
        int moduloValue = Integer.parseInt(value.split(CRON_FORWARD_SLASH)[1]);
        IntStream.rangeClosed(key.getMinRange(), key.getMaxRange())
                .filter(x -> x % moduloValue == 0)
                .forEach(result -> printResult(result));
    }

    private void printResult(String value) {
        try{
            printResult(Integer.parseInt(value));
        } catch (NumberFormatException ex){
            throw new NumberFormatException("Passed value is not a number");
        }
    }

    private void printResult(int result) {
        System.out.print(result + FIELD_SPACE_SEPARATOR);
    }

    private void printNumbers(String value) {
        Arrays.stream(value.split(FIELD_COMMA_SEPARATOR))
                .forEach(result -> printResult(result));
    }

    private void printNumbersFromRange(CronField key, String value) {
        Integer minRange = Integer.parseInt(value.split(FIELD_MINUS_SEPARATOR)[0]);
        Integer maxRange = Integer.parseInt(value.split(FIELD_MINUS_SEPARATOR)[1]);
        if(minRange < key.getMinRange()){
            throw new IllegalArgumentException("Wrong argument input");
        }
        if(maxRange > key.getMaxRange()){
            throw new IllegalArgumentException("Wrong argument input");
        }
        IntStream.rangeClosed(minRange, maxRange).forEach(result -> printResult(result));
    }

}
