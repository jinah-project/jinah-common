package com.obadaro.jinah.commons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.obadaro.jinah.common.util.Dates;

public class DatesTest {

    @Test
    public void tDaysBetween() {
        final Date a = new Date();
        final Date b = Dates.addDays(a, 5);

        Assert.assertTrue(5 == Dates.daysBetween(a, b));
        Assert.assertTrue(5 == Dates.daysBetween(b, a));
    }

    @Test
    public void tClearTime() {
        final Calendar c = Calendar.getInstance();
        Dates.clearTime(c);

        final int sum = c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE)
                + c.get(Calendar.SECOND) + c.get(Calendar.MILLISECOND);

        Assert.assertTrue(sum == 0);
    }

    @Test
    public void tMonthEdges() {
        final Calendar c = Calendar.getInstance();
        c.set(2012, Calendar.JANUARY, 12);
        Date dt = c.getTime();

        final SimpleDateFormat sdf = Dates.getFormatter(Dates.dd_MM_yyyy);
        Date[] edges = Dates.firstAndLastDayOfMonth(dt);

        Assert.assertTrue("01/01/2012".equals(sdf.format(edges[0])));
        Assert.assertTrue("31/01/2012".equals(sdf.format(edges[1])));

        c.set(2012, Calendar.APRIL, 12);
        dt = c.getTime();
        edges = Dates.firstAndLastDayOfMonth(dt);

        Assert.assertTrue("01/04/2012".equals(sdf.format(edges[0])));
        Assert.assertTrue("30/04/2012".equals(sdf.format(edges[1])));
    }

    @Test
    public void tGetFormatter() {
        final Calendar c = Calendar.getInstance();
        c.set(2012, Calendar.JANUARY, 1);
        Dates.clearTime(c);
        final Date dt = c.getTime();

        final String dma = Dates.getFormatter(Dates.dd_MM_yyyy).format(dt);
        final String dmaHms = Dates.getFormatter(Dates.dd_MM_yyyy_HH_mm_ss).format(dt);
        final String mdaHm = Dates.getFormatter(Dates.MM_dd_yyyy_hh_mm_a).format(dt);

        Assert.assertTrue("01/01/2012".equals(dma));
        Assert.assertTrue("01/01/2012 00:00:00".equals(dmaHms));
        Assert.assertTrue("01/01/2012 12:00 AM".equals(mdaHm));
    }

}
