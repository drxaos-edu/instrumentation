package com.github.drxaos.edu.instrumentation.proxy.app;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MessageService {

    public DateService dateService;

    public static long calendarDaysBetween(Calendar startCal, Calendar endCal) {
        Calendar start = Calendar.getInstance();
        start.setTimeZone(startCal.getTimeZone());
        start.setTimeInMillis(startCal.getTimeInMillis());

        Calendar end = Calendar.getInstance();
        end.setTimeZone(endCal.getTimeZone());
        end.setTimeInMillis(endCal.getTimeInMillis());

        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        return TimeUnit.MILLISECONDS.toDays(Math.abs(end.getTimeInMillis() - start.getTimeInMillis()));
    }

    public String format(Message message) {
        if (message.getFormatted() != null) {
            return message.getFormatted();
        }

        String user = StringUtils.capitalize(message.getUsername());
        String time;

        if (DateUtils.isSameLocalTime(message.getDate(), dateService.now())) {
            time = "now";
        } else {
            long daysBetween = calendarDaysBetween(message.getDate(), dateService.now());
            if (daysBetween <= 5) {
                time = "" + daysBetween + " days ago";
            } else {
                time = DateFormatUtils.format(message.getDate(), "dd.MM.yyyy 'at' HH:mm:ss");
            }
        }

        String result = user + " (" + time + "): " + message.getText();
        message.setFormatted(result);
        return result;
    }


}
