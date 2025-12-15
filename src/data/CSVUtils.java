package data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class CSVUtils {

    public static String clean(String s) {
        return s == null ? "" : s.trim();
    }

    public static LocalDate parseDate(String s) {
        return LocalDate.parse(clean(s));
    }

    public static LocalTime parseTime(String s) {
        return LocalTime.parse(clean(s));
    }

    public static List<String> parseList(String s) {
        return Arrays.asList(clean(s).split("\\|"));
    }

    // Splits CSV line while preserving commas inside quotes
    public static String[] split(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    }
}
