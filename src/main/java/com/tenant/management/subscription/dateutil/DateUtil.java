package com.tenant.management.subscription.dateutil;

import java.util.Date;

public class DateUtil {
    public static boolean isDateInFuture(Date date) {
        return date.after(new Date());
    }
}
