package com.github.drxaos.edu.instrumentation.proxy;


import com.github.drxaos.edu.instrumentation.proxy.app.DateService;
import com.github.drxaos.edu.instrumentation.proxy.app.Message;
import com.github.drxaos.edu.instrumentation.proxy.app.MessageService;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Test {
    public static void main(String[] args) throws Exception {
        TestDoublesControl ctrl = new TestDoublesControl();

        Calendar calendar = ctrl.wrap("calendar", new GregorianCalendar(2000, 1, 1));
        Message message = ctrl.wrap("message", new Message(calendar, "user", "hello everybody!"));
        DateService dateService = ctrl.wrap("dateService", new DateService());
        Calendar now = new GregorianCalendar(2000, 1, 7);

        MessageService messageService = new MessageService();
        messageService.dateService = dateService;

        ctrl.plan();

        message.getFormatted();
        message.getUsername();
        message.getDate();
        dateService.now();
        ctrl.overrideResult(now);
        calendar.get(Calendar.MILLISECOND);
        calendar.get(Calendar.SECOND);
        calendar.get(Calendar.MINUTE);
        calendar.get(Calendar.HOUR_OF_DAY);
        calendar.get(Calendar.DAY_OF_YEAR);
        message.getDate();
        dateService.now();
        ctrl.overrideResult(now);
        calendar.getTimeZone();
        calendar.getTimeInMillis();
        message.getDate();
        calendar.get(Calendar.DAY_OF_MONTH);
        calendar.get(Calendar.MONTH);
        calendar.get(Calendar.YEAR);
        calendar.get(Calendar.HOUR_OF_DAY);
        calendar.get(Calendar.MINUTE);
        calendar.get(Calendar.SECOND);
        message.getText();
        message.setFormatted("User (01.02.2000 at 00:00:00): hello everybody!");

        ctrl.start();

        messageService.format(message);

        ctrl.end();
    }
}
