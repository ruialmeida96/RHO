package challenge.RHO.Utils;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    private static DateTimeFormatter formato_data = DateTimeFormatter.ofPattern("uuuu/MM/dd");
    private static DateTimeFormatter formato_time = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static Date return_current_date(){
        java.util.Date data = new java.util.Date(System.currentTimeMillis());
        return new Date(data.getTime());
    }

    public static Time return_current_time(){
        LocalTime localTime = LocalTime.now();
        return Time.valueOf(localTime.format(formato_time));
    }

}
