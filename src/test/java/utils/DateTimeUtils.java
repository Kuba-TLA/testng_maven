package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyy_HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    //This is specifically used as part of screenshot name to avoid duplicates
    public String currentDateTime = formatter.format(now);
}
