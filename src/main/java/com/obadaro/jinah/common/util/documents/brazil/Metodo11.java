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
package com.obadaro.jinah.common.util.documents.brazil;


/**
 * @author Roberto Badaro
 */
public class Metodo11 {

    /**
     * Checks if the number is valid.
     * 
     * @param number
     * @param validationDigits
     * @return
     */
    public static boolean isValid(long number, int[] validationDigits) {

        String s = String.valueOf(number);
        int[] nums = new int[s.length()];

        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.valueOf(s.charAt(i));
        }

        return isValid(nums, validationDigits);
    }

    /**
     * Checks if the number is valid.
     * 
     * @param number
     * @param validationDigits
     * @return
     */
    public static boolean isValid(int[] number, int[] validationDigits) {

        return check(number, validationDigits);
    }

    /**
     * Don't accept sequence of repeated digits like 11111111111, etc.
     * 
     * @param number
     * @param validationDigits
     * @return
     */
    protected static boolean check(final int[] number, final int[] validationDigits) {

        final int len = number.length - 2;
        int cnt = 0;
        for (int i = 1; i <= len; i++) {
            if (number[i - 1] == number[i]) {
                cnt++;
            }
        }

        if (cnt == len) {
            return false;
        }

        return checkDigit(number, true, validationDigits) && checkDigit(number, false, validationDigits);
    }

    /**
     * @param number
     * @param firstDigit
     * @return
     */
    protected static boolean checkDigit(final int[] number,
                                        final boolean firstDigit,
                                        final int[] validationDigits) {

        final int digitIndex = (firstDigit ? number.length - 2 : number.length - 1);
        final int start = (firstDigit ? 1 : 0);
        int total = 0;
        int remainder = 0;
        int digit = 0;

        for (int i = start, k = 0; i < validationDigits.length; i++, k++) {
            total += validationDigits[i] * number[k];
        }

        remainder = total % 11;

        if (remainder < 2) {
            digit = 0;
        } else {
            digit = 11 - remainder;
        }

        return (digit == number[digitIndex]);
    }

}
