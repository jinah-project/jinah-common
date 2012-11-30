package com.obadaro.jinah.commons.util;

import org.junit.Assert;
import org.junit.Test;

import com.obadaro.jinah.common.util.Strings;

public class StringsTest {

    @Test
    public void tFillChar() {
        final String expected = "aaaa";
        final String returned = Strings.fill('a', expected.length());

        Assert.assertTrue(expected.equals(returned));
    }

    @Test
    public void tFillString() {
        final String expected = "nananana";
        final String returned = Strings.fill("na", 4);

        Assert.assertTrue(expected.equals(returned));
    }

    @Test
    public void tIsBlank() {
        Assert.assertTrue(Strings.isBlank(" "));
        Assert.assertTrue(Strings.isBlank(""));
        Assert.assertTrue(Strings.isBlank(null));
    }

    @Test
    public void tIsEmpty() {
        Assert.assertFalse(Strings.isEmpty(" "));
        Assert.assertTrue(Strings.isEmpty(""));
        Assert.assertTrue(Strings.isEmpty(null));
    }

    @Test
    public void tPad() {
        final String target = "TESTE";
        Assert.assertTrue(("0000" + target).equals(Strings.leftPad('0', 9, target)));
        Assert.assertTrue((target + "0000").equals(Strings.rightPad('0', 9, target)));
    }

}
