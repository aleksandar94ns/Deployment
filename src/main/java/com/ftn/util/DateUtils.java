package com.ftn.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alex on 3/1/17.
 */
public class DateUtils {

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long difference = date2.getTime() - date1.getTime();
        return timeUnit.convert(difference, TimeUnit.MILLISECONDS);
    }
}
