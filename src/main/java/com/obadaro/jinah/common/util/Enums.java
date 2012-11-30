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
 * Utility methods for Enum.
 * 
 * @author Roberto Badaro
 */
public class Enums {

    /**
     * Returns the enum value for name parameter, avoiding a NullPointerException when executing
     * {@link Enum#valueOf(Class, String)}.
     * 
     * @param enumType
     * @param name
     * @return Enum value for name parameter, or {@code null} if {@code name} is null or empty.
     */
    public static <E extends Enum<E>> E valueOf(final Class<E> enumType, final String name) {

        if (Strings.isNotBlank(name)) {
            return Enum.valueOf(enumType, name);
        } else {
            return null;
        }
    }

    /**
     * Returns the enum name, avoiding a NullPointerException when executing {@link Enum#name()}.
     * 
     * @param enumValue
     * @return The enum name, or {@code null} if {@code enumValue} is null.
     */
    public static <E extends Enum<E>> String name(final E enumValue) {

        if (enumValue != null) {
            return enumValue.name();
        } else {
            return null;
        }
    }
}
