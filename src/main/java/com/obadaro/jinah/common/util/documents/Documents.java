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
package com.obadaro.jinah.common.util.documents;

import java.io.CharArrayWriter;

import com.obadaro.jinah.common.util.Preconditions;
import com.obadaro.jinah.common.util.Strings;

/**
 * @author Roberto Badaro
 */
public abstract class Documents {

    static final char MASK_CHAR = '@';

    protected String mask;
    protected int docLen;

    /**
     * Default constructor.
     */
    protected Documents() {
        // NOOP
    }

    protected Documents(final String mask, final int docLen) {
        this.mask = mask;
        this.docLen = docLen;
    }

    /**
     * @param doc
     * @return
     */
    public String format(final long doc) {
        final String s = String.valueOf(doc);
        return mask(s);
    }

    /**
     * @param doc
     * @return
     */
    public String mask(String doc) {
        final int dif = docLen - doc.length();
        if (dif > 0) {
            doc = Strings.fill('0', dif) + doc;
        }
        return mask(doc, mask);
    }

    /**
     * @param doc
     * @return
     */
    public String unmask(final String doc) {
        return unmask(doc, mask);
    }

    /**
     * @param doc
     * @return
     */
    public long parse(final String doc) {
        return parse(doc, docLen, mask);
    }

    /**
     * @param doc
     * @return
     */
    public abstract boolean validate(long doc);

    /**
     * @param doc
     * @return
     */
    public abstract boolean validate(String doc);

    /**
     * <p>
     * Masks the String with the informed pattern. The method just applies the
     * pattern, don't adjusts the value.
     * </p>
     * <code><pre>
     * mask("01345011", "@@.@@@-@@@") -> "01.345-011"
     * </pre></code>
     * 
     * @param value
     *            String to be masked
     * @param pattern
     *            Mask pattern to apply
     * @return
     */
    protected String mask(final String value, final String pattern) {
        if (value == null) {
            return null;
        }
        Preconditions.checkArgument(pattern != null, "pattern can't be null");
        Preconditions.checkArgument(pattern.indexOf(MASK_CHAR) != -1,
                "invalid pattern '%s'",
                pattern);
        Preconditions.checkArgument(value.length() < pattern.length(),
                "value length must be less than pattern length");

        final char[] merge = pattern.toCharArray();
        final char[] valueChars = value.toCharArray();
        final int maskLen = merge.length;
        int vi = 0;

        for (int i = 0; i < maskLen; i++) {
            final char c = merge[i];
            if (MASK_CHAR == c) {
                merge[i] = valueChars[vi];
                vi++;
            }
        }

        return new String(merge);
    }

    /**
     * <p>
     * Unmask a masked String based on informed pattern. The masked value and
     * the pattern must have the same size.
     * </p>
     * <code><pre>
     * unmask("01.345-011", "@@.@@@-@@@") -> "01345011"
     * </pre></code>
     * 
     * @param masked
     * @param pattern
     * @return
     */
    protected String unmask(final String masked, final String pattern) {
        if (masked == null) {
            return null;
        }
        Preconditions.checkArgument(pattern != null, "pattern can't be null");
        Preconditions.checkArgument(pattern.indexOf(MASK_CHAR) != -1,
                "invalid pattern '%s'",
                pattern);
        Preconditions.checkArgument(masked.length() == pattern.length(),
                "masked length must be equal to pattern length");

        final char[] patternChars = pattern.toCharArray();
        final char[] maskedChars = masked.toCharArray();
        final int len = patternChars.length;

        final CharArrayWriter writer = new CharArrayWriter(len);

        for (int i = 0; i < len; i++) {
            if (MASK_CHAR == patternChars[i]) {
                writer.append(maskedChars[i]);
            }
        }

        return writer.toString();
    }

    /**
     * Transforms the characters of a String into int and fills an int array.
     * 
     * @param s
     *            String filled only with numbers.
     * @return
     */
    protected int[] toIntArray(final String s) {
        final int len = s.length();
        final int[] ret = new int[len];
        for (int i = 0; i < len; i++) {
            ret[i] = Integer.parseInt(s.substring(i, i + 1));
        }
        return ret;
    }

    /**
     * 
     * @param value
     * @param maxLen
     * @param mask
     * @return
     */
    protected long parse(String value, final int maxLen, final String mask) {
        Preconditions.checkArgument(Strings.isNotBlank(value), "value can't be null or empty.");
        Preconditions.checkArgument(Strings.isNotBlank(mask), "mask can't be null or empty.");

        final int len = value.length();
        final int formatedLen = mask.length();
        Preconditions.checkArgument(len <= maxLen || len == formatedLen,
                "Wrong value length: %s",
                len);

        if (len == formatedLen) {
            value = unmask(value, mask);
        }

        return Long.valueOf(value);
    }

}
