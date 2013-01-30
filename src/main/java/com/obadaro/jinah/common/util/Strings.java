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

/**
 * Utility methods for String manipulation.
 * 
 * @author Roberto Badaro
 */
public class Strings {

    /**
     * Returns {@code true} if the {@code String} is null or empty. Performs {@link String#trim()}
     * to check emptiness.
     * 
     * @param s
     *            String
     * @return {@code true} if the String is null or empty.
     */
    public static boolean isBlank(final String s) {

        return (s == null || s.isEmpty() || s.trim().isEmpty());
    }

    /**
     * Returns {@code true} if the {@code String} is not null and not empty. Performs
     * {@link String#trim()} to check emptiness.
     * 
     * @param s
     *            String
     * @return {@code true} if the {@code String} is not null and not empty.
     */
    public static boolean isNotBlank(final String s) {

        return !isBlank(s);
    }

    /**
     * Returns {@code true} if the String is null or empty. Note that a String filled with spaces is
     * not empty.
     * 
     * @param s
     *            String
     * @return {@code true} if the String is null or empty.
     */
    public static boolean isEmpty(final String s) {

        return (s == null || s.isEmpty());
    }

    /**
     * Returns {@code s} if not blank, else returns the {@code defaultString}.
     * 
     * @param s
     *            String to verify.
     * @param defaultString
     *            String to return if the first is blank.
     * @return {@code s} if not blank, else returns the {@code defaultString}.
     * @see Strings#isBlank(String)
     */
    public static String ifBlank(String s, String defaultString) {

        return (!isBlank(s) ? s : defaultString);
    }

    /**
     * Returns {@code true} if all parameters are blank, empty or null.
     * 
     * @param string
     * @return {@code true} if all parameters are blank.
     * @see Strings#isNotBlank(String)
     */
    public static boolean isAllBlank(String... string) {

        if (string == null || string.length == 0) {
            return true;
        }
        for (String s : string) {
            if (Strings.isNotBlank(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a String filled with the repetition of the specified character.
     * 
     * <pre>
     * <code>
     * fill('A', 5) returns "AAAAA"
     * </code>
     * </pre>
     * 
     * @param c
     *            char
     * @param length
     * @return New String filled with the specified character.
     */
    public static String fill(final char c, final int length) {

        final char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = c;
        }
        return new String(chars);
    }

    /**
     * Creates a new String filled with the repetition of the String parameter.
     * 
     * <pre>
     * <code>
     * fill("Abc", 3) returns "AbcAbcAbc"
     * </code>
     * </pre>
     * 
     * @param s
     *            String
     * @param length
     *            Quantas repetições.
     * @return New String filled with the repetition of the String parameter.
     */
    public static String fill(final String s, final int length) {

        final int slen = s.length();
        final int len = slen * length;
        final char[] chars = new char[len];
        final char[] copy = s.toCharArray();

        for (int i = 0; i < len; i = (i + slen)) {
            System.arraycopy(copy, 0, chars, i, slen);
        }

        return new String(chars);
    }

    /**
     * Returns the source String padded with filler character at the specified side.
     * 
     * <pre>
     * <code>
     *  // Left padding
     * pad('0', 6, "A1", true)  returns "0000A1"
     * 
     *  // Right padding
     * pad('0', 6, "A1", false) returns "A10000"
     * </code>
     * </pre>
     * 
     * @param filler
     *            Filler char
     * @param maxLen
     *            Max lenght of the resultant String
     * @param source
     *            Source String
     * @param leftPad
     *            If {@code true}, do a left padding, otherwise, do a right padding.
     * @return New padded String.
     */
    public static String pad(final char filler, final int maxLen, final String source, final boolean leftPad) {

        Preconditions.checkArgument(maxLen > 0, "maxLen must be greater than 0.");
        Preconditions.checkArgument(source != null, "source can't be null.");

        final int dif = maxLen - source.length();
        if (dif > 0) {
            final String fill = fill(filler, dif);
            if (leftPad) {
                return fill + source;
            } else {
                return source + fill;
            }
        } else {
            return source;
        }
    }

    /**
     * Returns the source String padded with filler character at the left side.
     * 
     * <pre>
     * <code>
     * leftPad('0', 6, "A1")  returns "0000A1"
     * </code>
     * </pre>
     * 
     * @param filler
     *            Filler char
     * @param maxLen
     *            Max lenght of the resultant String
     * @param source
     *            Source String
     * @return New left padded String.
     */
    public static String leftPad(final char filler, final int maxLen, final String source) {

        return pad(filler, maxLen, source, true);
    }

    /**
     * Returns the source String padded with filler character at the right side.
     * 
     * <pre>
     * <code>
     * rightPad('0', 6, "A1") returns "A10000"
     * </code>
     * </pre>
     * 
     * @param filler
     *            Filler char
     * @param maxLen
     *            Max lenght of the resultant String
     * @param source
     *            Source String
     * @return New right padded String.
     */
    public static String rightPad(final char filler, final int maxLen, final String source) {

        return pad(filler, maxLen, source, false);
    }

    /**
     * Returns the leftmost len characters of a String. The method is null-safe.
     * 
     * @param source
     *            Source String. May be null.
     * @param len
     *            Number of characters to retrieve from left. Must be &gt;= 0.
     * @return
     */
    public static String left(final String source, final int len) {

        Preconditions.checkArgument(len >= 0, "len must be >= 0.");

        if (source != null && source.length() > len) {
            return source.substring(0, len);
        } else {
            return source;
        }
    }

    /**
     * Returns the rightmost len characters of a String. The method is null-safe.
     * 
     * @param source
     *            Source String. May be null.
     * @param len
     *            Number of characters to retrieve from right. Must be &gt;= 0.
     * @return
     */
    public static String right(final String source, final int len) {

        Preconditions.checkArgument(len >= 0, "len must be >= 0.");

        if (source != null && source.length() > len) {
            return source.substring(source.length() - len);
        } else {
            return source;
        }
    }

}
