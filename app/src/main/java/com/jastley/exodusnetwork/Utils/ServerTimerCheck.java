package com.jastley.exodusnetwork.Utils;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;

public class ServerTimerCheck {

    public static boolean isXurAvailable() {

        ZonedDateTime zdtUtcNow = ZonedDateTime.now(ZoneOffset.UTC);
        DayOfWeek utcDayNow = zdtUtcNow.getDayOfWeek();

        //check if we're currently within the window for Xur
        if(utcDayNow.getValue() >= DayOfWeek.FRIDAY.getValue() &&
                utcDayNow.getValue() <= DayOfWeek.TUESDAY.getValue()) {

            //check if after reset time if currently Friday
            if(utcDayNow.getValue() == DayOfWeek.FRIDAY.getValue()) {

                //5PM UTC time
                return zdtUtcNow.getHour() >= 17;

            }
            //check if before reset time on Tuesday
            else if(utcDayNow.getValue() == DayOfWeek.TUESDAY.getValue()) {

                //5PM UTC time
                return zdtUtcNow.getHour() < 17;
            }
            //Currently Saturday-Monday (UTC), Xur's definitely in-game
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    public static ZonedDateTime getUTCNow() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }

    public static ZonedDateTime getResetTime() {

        ZonedDateTime zdtUtcNow = ZonedDateTime.now(ZoneOffset.UTC);
        DayOfWeek utcDayNow = zdtUtcNow.getDayOfWeek();

        int daysTilReset = ((DayOfWeek.TUESDAY.getValue() - utcDayNow.getValue() + 7) % 7);
        ZonedDateTime thisWeekReset = zdtUtcNow.plusDays(daysTilReset);
        return ZonedDateTime.of(thisWeekReset.getYear(),
                thisWeekReset.getMonth().getValue(),
                thisWeekReset.getDayOfMonth(),
                17, 0, 0, 0, ZoneOffset.UTC);
    }

    public static ZonedDateTime getXurArrivalTime() {

        ZonedDateTime zdtUtcNow = ZonedDateTime.now(ZoneOffset.UTC);
        DayOfWeek utcDayNow = zdtUtcNow.getDayOfWeek();

        int daysTilXur = ((DayOfWeek.FRIDAY.getValue() - utcDayNow.getValue() + 7) % 7);
        ZonedDateTime thisWeekReset = zdtUtcNow.plusDays(daysTilXur);

        return ZonedDateTime.of(thisWeekReset.getYear(),
                thisWeekReset.getMonth().getValue(),
                thisWeekReset.getDayOfMonth(),
                17, 0, 0, 0, ZoneOffset.UTC);
    }
}
