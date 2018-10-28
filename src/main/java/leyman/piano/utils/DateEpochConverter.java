package leyman.piano.utils;

import java.util.Date;

public class DateEpochConverter {

    //Date conversation methods
    public static Date epochToDate(long epoch) {
        return new Date(epoch * 1000);
    }

    public static String dateToEpoch(Date date) {
        if (date != null) {
            return Long.toString(date.getTime() / 1000);
        }
        return null;
    }
}
