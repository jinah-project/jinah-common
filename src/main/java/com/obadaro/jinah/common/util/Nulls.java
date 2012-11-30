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
 * 
 * @author Roberto Badaro
 */
public class Nulls {

    /**
     * Returns the non null value.
     * 
     * @param value
     *            A value.
     * @param defaultValue
     *            Default value to return if {@code value} is null.
     * @return The non null value.
     */
    public static <T> T nvl(final T value, final T defaultValue) {
        if (value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }

    /**
     * Same as {@link #nvl(Object, Object)}
     */
    public static <T> T ifNull(final T value, final T defaultValue) {
        return nvl(value, defaultValue);
    }

    /**
     * Same as {@link #nvl(Object, Object)}
     */
    public static <T> T ifnull(final T value, final T defaultValue) {
        return nvl(value, defaultValue);
    }

}
