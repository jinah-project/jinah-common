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

import com.obadaro.jinah.common.util.Strings;
import com.obadaro.jinah.common.util.documents.Documents;

/**
 * @author Roberto Badaro
 */
public abstract class Metodo11ValidableDocument extends Documents {

    private final int formattedLen;
    private final int[] validationDigits;

    protected Metodo11ValidableDocument(final String mask, final int docLen, final int[] validationDigits) {

        super(mask, docLen);
        formattedLen = mask.length();
        this.validationDigits = validationDigits;
    }

    /*
     * (non-Javadoc)
     * @see com.obadaro.jinah.common.util.documents.Documents#validate(long)
     */
    @Override
    public boolean validate(final long doc) {

        String s = String.valueOf(doc);
        final int dif = docLen - s.length();
        if (dif > 0) {
            s = Strings.fill('0', dif) + s;
        }
        return validate(s);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.obadaro.jinah.common.util.documents.Documents#validate(java.lang.String)
     */
    @Override
    public boolean validate(String doc) {

        if (doc == null) {
            return false;
        }
        if (doc.length() == formattedLen) {
            doc = unmask(doc, mask);
        }
        if (doc.length() != docLen) {
            return false;
        }

        try {
            final int[] cpfNumbers = toIntArray(doc);
            return Metodo11.isValid(cpfNumbers, validationDigits);
        } catch (final Exception e) {
            return false;
        }
    }

}
