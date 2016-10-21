package com.github.drxaos.edu.instrumentation.proxy;


import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Example {
    public static void main(String[] args) throws Exception {
        TestDoublesControl ctrl = new TestDoublesControl();

        Calendar calendar1 = ctrl.wrap("calendar1", new GregorianCalendar(2000, 1, 1));
        Calendar calendar2 = ctrl.wrap("calendar2", new GregorianCalendar(2004, 5, 6));

        ctrl.plan();

        calendar1.get(Calendar.MILLISECOND);
        calendar2.get(Calendar.MILLISECOND);
        calendar1.get(Calendar.SECOND);
        calendar2.get(Calendar.SECOND);
        calendar1.get(Calendar.MINUTE);
        calendar2.get(Calendar.MINUTE);
        calendar1.get(Calendar.HOUR_OF_DAY);
        calendar2.get(Calendar.HOUR_OF_DAY);
        calendar1.get(Calendar.DAY_OF_YEAR);
        calendar2.get(Calendar.DAY_OF_YEAR);

        ctrl.start();

        DateUtils.isSameLocalTime(calendar1, calendar2);

        ctrl.end();
    }
}
