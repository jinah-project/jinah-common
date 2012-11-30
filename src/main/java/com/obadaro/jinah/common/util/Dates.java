/* 
 * JINAH Project - Java Is Not A Hammer
 * http://obadaro.com/jinah
 * 
 * Copyright (C) 2010-2012 Roberto Badaro 
 * and individual contributors by the @authors tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.obadaro.jinah.common.util;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Utility methods for Date manipulation.
 * 
 * @author Roberto Badaro
 */
public class Dates {

    public static final String dd_MM_yyyy = "dd/MM/yyyy";
    public static final String dd_MM_yyyy_HH_mm = "dd/MM/yyyy HH:mm";
    public static final String dd_MM_yyyy_HH_mm_ss = "dd/MM/yyyy HH:mm:ss";

    public static final String MM_dd_yyyy = "MM/dd/yyyy";
    public static final String MM_dd_yyyy_HH_mm = "MM/dd/yyyy HH:mm";
    public static final String MM_dd_yyyy_HH_mm_ss = "MM/dd/yyyy HH:mm:ss";

    public static final String MM_dd_yyyy_hh_mm_a = "MM/dd/yyyy hh:mm a";
    public static final String MM_dd_yyyy_hh_mm_ss_a = "MM/dd/yyyy hh:mm:ss a";

    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String HH_mm = "HH:mm";

    public static final String hh_mm_ss_a = "hh:mm:ss a";
    public static final String hh_mm_a = "hh:mm a";

    public static final long ONE_MINUTE = 60 * 1000;
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    public static final long ONE_DAY_MILLIS = 24 * ONE_HOUR;

    protected static final Locale DEFAULT_LOCALE = Locale.getDefault();
    protected static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();

    static final ThreadLocal<Map<String, WeakReference<SimpleDateFormat>>> SDF_CACHE =
            new ThreadLocal<Map<String, WeakReference<SimpleDateFormat>>>();


    /**
     * Returns a calendar using the default time zone and locale.
     * 
     * @return
     */
    public static Calendar calendar() {

        return Calendar.getInstance();
    }

    /**
     * Returns a calendar using the time zone and locale provided.
     * 
     * @param timeZone
     * @param locale
     * @return
     */
    public static Calendar calendar(TimeZone timeZone, Locale locale) {

        return Calendar.getInstance(timeZone, locale);
    }

    /**
     * Returns a calendar, configured with date, using the default time zone and locale.
     * 
     * @param date
     *            Date to set in Calendar.
     * @return
     */
    public static Calendar calendar(final Date date) {

        return calendar(date, false);
    }

    /**
     * Returns a calendar, configured with date, using the default time zone and locale.
     * 
     * @param date
     *            Date to set in Calendar.
     * @param clearTime
     *            If {@code true}, clears the time portion - sets {@link Calendar#HOUR_OF_DAY},
     *            {@link Calendar#MINUTE}, {@link Calendar#SECOND} and {@link Calendar#MILLISECOND}
     *            to 0 (zero).
     * @return
     */
    public static Calendar calendar(final Date date, final boolean clearTime) {

        return calendar(date, clearTime, DEFAULT_TIME_ZONE, DEFAULT_LOCALE);
    }

    /**
     * Returns a calendar, configured with date, using provided time zone and locale.
     * 
     * @param date
     *            Date to set in Calendar.
     * @param clearTime
     *            If {@code true}, clears the time portion - sets {@link Calendar#HOUR_OF_DAY},
     *            {@link Calendar#MINUTE}, {@link Calendar#SECOND} and {@link Calendar#MILLISECOND}
     *            to 0 (zero).
     * @param timeZone
     * @param locale
     * @return
     */
    public static Calendar calendar(final Date date, final boolean clearTime, TimeZone timeZone, Locale locale) {

        Preconditions.checkArgument(date != null, "date can't be null.");
        Preconditions.checkArgument(timeZone != null, "timeZone can't be null.");
        Preconditions.checkArgument(locale != null, "locale can't be null.");

        final Calendar c = Calendar.getInstance(timeZone, locale);
        c.setTime(date);
        if (clearTime) {
            clearTime(c);
        }
        return c;
    }

    /**
     * Returns the first and last day of the month/year of reference date.
     * 
     * @param refDate
     *            Reference date.
     * @return {first-day, last-day}
     */
    public static Date[] firstAndLastDayOfMonth(final Date refDate) {

        return firstAndLastDayOfMonth(refDate, DEFAULT_TIME_ZONE, DEFAULT_LOCALE);
    }

    /**
     * Returns the first and last day of the month/year of reference date.
     * 
     * @param refDate
     *            Reference date.
     * @param timeZone
     * @param locale
     * @return {first-day, last-day}
     */
    public static Date[] firstAndLastDayOfMonth(final Date refDate, TimeZone timeZone, Locale locale) {

        final Calendar c = calendar(refDate, true, timeZone, locale);

        c.set(Calendar.DAY_OF_MONTH, 1);
        final Date beginDt = c.getTime();

        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        final Date endDt = c.getTime();

        return new Date[] { beginDt, endDt };
    }

    /**
     * Clears the time portion of the Calendar. Sets {@link Calendar#HOUR_OF_DAY},
     * {@link Calendar#MINUTE}, {@link Calendar#SECOND} and {@link Calendar#MILLISECOND} to 0
     * (zero).
     * 
     * @param calendar
     * @return
     */
    public static Calendar clearTime(final Calendar calendar) {

        Preconditions.checkArgument(calendar != null, "calendar can't be null.");

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    public static Date addDays(final Date date, final int days) {

        return add(date, Calendar.DAY_OF_MONTH, days, DEFAULT_TIME_ZONE, DEFAULT_LOCALE);
    }

    public static Date addMonths(final Date date, final int months) {

        return add(date, Calendar.MONTH, months, DEFAULT_TIME_ZONE, DEFAULT_LOCALE);
    }

    public static Date addYears(final Date date, final int years) {

        return add(date, Calendar.YEAR, years, DEFAULT_TIME_ZONE, DEFAULT_LOCALE);
    }

    /**
     * Convenience method for simple add/subtract operation. If you need to do more operations on
     * same Date, get a Calendar instance and do all the work on your own code.
     * 
     * @param date
     * @param field
     * @param amount
     * @param timeZone
     * @param locale
     * @return
     * 
     * @see Calendar
     */
    public static Date add(final Date date,
                           final int field,
                           final int amount,
                           TimeZone timeZone,
                           Locale locale) {

        final Calendar c = calendar(date, false, timeZone, locale);
        c.add(field, amount);
        return c.getTime();
    }

    public static boolean isBetween(final Date a, final Date b, final Date date) {

        return isBetween(a, b, date, false);
    }

    public static boolean isBetween(Date a, Date b, Date date, final boolean clearTime) {

        return isBetween(a, b, date, clearTime, DEFAULT_TIME_ZONE, DEFAULT_LOCALE);
    }

    public static boolean isBetween(Date a,
                                    Date b,
                                    Date date,
                                    final boolean clearTime,
                                    TimeZone timeZone,
                                    Locale locale) {

        Preconditions.checkArgument(a != null, "Date 'a' can't be null.");
        Preconditions.checkArgument(b != null, "Date 'b' can't be null.");
        Preconditions.checkArgument(date != null, "Date 'date' can't be null.");
        Preconditions.checkArgument(timeZone != null, "timeZone is null");
        Preconditions.checkArgument(locale != null, "locale is null");

        if (clearTime) {
            Calendar cal = calendar(timeZone, locale);

            cal.setTime(a);
            a = clearTime(cal).getTime();

            cal.setTime(b);
            b = clearTime(cal).getTime();

            cal.setTime(date);
            date = clearTime(cal).getTime();
        }

        return date.compareTo(a) <= 0 && date.compareTo(b) >= 0;
    }

    /**
     * Returns the number of days between dates A and B. The calculation does
     * not consider the time portion (HHmmss).
     * 
     * @param a
     *            Begin date.
     * @param b
     *            End date.
     * @return Number of days between dates A and B.
     */
    public static int daysBetween(final Date a, final Date b) {

        return daysBetween(a, b, DEFAULT_TIME_ZONE, DEFAULT_LOCALE);
    }

    /**
     * Returns the number of days between dates A and B. The calculation does
     * not consider the time portion (HHmmss).
     * 
     * @param a
     *            Begin date.
     * @param b
     *            End date.
     * @param timeZone
     * @param locale
     * @return Number of days between dates A and B.
     */
    public static int daysBetween(final Date a, final Date b, TimeZone timeZone, Locale locale) {

        Preconditions.checkArgument(a != null, "Date 'a' can't be null.");
        Preconditions.checkArgument(b != null, "Date 'b' can't be null.");
        Preconditions.checkArgument(timeZone != null, "timeZone is null");
        Preconditions.checkArgument(locale != null, "locale is null");

        final long millisA = calendar(a, true, timeZone, locale).getTimeInMillis();
        final long millisB = calendar(b, true, timeZone, locale).getTimeInMillis();
        long millisDif = millisA - millisB;

        if (millisDif < 0) {
            millisDif *= -1;
        }

        return (int) (millisDif / ONE_DAY_MILLIS);
    }

    /**
     * As {@link SimpleDateFormat} is not thread-safe, this method uses a {@link ThreadLocal} cache
     * to promote reutilization, preventing conflicts.
     * The returned SimpleDateFormat is configured to be not lenient.
     * 
     * @param pattern
     * @return A SimpleDateFormat using the {@code pattern} and the default {@link Locale}.
     * @see SimpleDateFormat#setLenient(boolean)
     */
    public static SimpleDateFormat getFormatter(final String pattern) {

        return getFormatter(pattern, DEFAULT_LOCALE, DEFAULT_TIME_ZONE);
    }

    /**
     * As {@link SimpleDateFormat} is not thread-safe, this method uses a {@link ThreadLocal} cache
     * to promote reutilization, preventing conflicts.
     * The returned SimpleDateFormat is configured to be not lenient.
     * 
     * @param pattern
     * @param locale
     * @param timeZone
     * @return A SimpleDateFormat using the {@code pattern} and {@code locale}.
     * @see SimpleDateFormat#setLenient(boolean)
     */
    public static SimpleDateFormat getFormatter(final String pattern,
                                                final Locale locale,
                                                final TimeZone timeZone) {

        Preconditions.checkArgument(Strings.isNotBlank(pattern), "Invalid pattern: %s", pattern);
        Preconditions.checkArgument(locale != null, "locale is null");
        Preconditions.checkArgument(timeZone != null, "timeZone is null");

        Map<String, WeakReference<SimpleDateFormat>> map = SDF_CACHE.get();
        if (map == null) {
            map = new HashMap<String, WeakReference<SimpleDateFormat>>();
            SDF_CACHE.set(map);
        }

        final String key = pattern + "|" + locale.toString() + "|" + timeZone.getID();

        SimpleDateFormat sdf = null;
        WeakReference<SimpleDateFormat> cached = map.get(key);

        if (cached != null && cached.get() != null) {
            sdf = cached.get();
        } else {
            sdf = new SimpleDateFormat(pattern, locale);
            sdf.setTimeZone(timeZone);
            sdf.setLenient(false);
            map.put(key, new WeakReference<SimpleDateFormat>(sdf));
        }

        return sdf;
    }

}
