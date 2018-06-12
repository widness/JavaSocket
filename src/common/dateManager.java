package common;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class dateManager {

    public int getMonth(){
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getMonthValue();
    }
}
