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
 * CEP - Código de Endereçamento Postal.<br>
 * Pattern: {@code 99.999-999}
 * 
 * @author Roberto Badaro
 */
public class CEP extends Documents {

    public static final String MASK = "@@.@@@-@@@";
    public static final int DOC_LENGTH = 8;

    public CEP() {
        super(MASK, DOC_LENGTH);
    }

    /**
     * No validation performed. Always return {@code true}.
     * 
     * @see com.obadaro.jinah.common.util.documents.Documents#validate(long)
     */
    @Override
    public boolean validate(final long doc) {
        return true;
    }

    /**
     * No validation performed. Just return {@code true} if {@code doc} argument
     * is not {@code null}.
     * 
     * @see com.obadaro.jinah.common.util.documents.Documents#validate(java.lang.String)
     */
    @Override
    public boolean validate(final String doc) {
        return Strings.isNotBlank(doc);
    }
}