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
package com.obadaro.jinah.commons.util.documents.brazil;

import org.junit.Assert;
import org.junit.Test;

import com.obadaro.jinah.common.util.documents.brazil.CNPJ;

/**
 * 
 * @author Roberto Badaro
 */
public class CNPJTest {

    private final CNPJ cnpj = new CNPJ();

    @Test
    public void tMask() {
        Assert.assertTrue("11.111.111/1111-11".equals(cnpj.mask("11111111111111")));
        Assert.assertTrue("01.111.111/1111-11".equals(cnpj.mask("1111111111111")));
    }

    @Test
    public void tFormat() {
        Assert.assertTrue("11.111.111/1111-11".equals(cnpj.format(11111111111111L)));
        Assert.assertTrue("01.111.111/1111-11".equals(cnpj.format(1111111111111L)));
    }

    @Test
    public void tValidateString() {
        final String validCnpj = "57.742.897/0001-46";
        final String invalidCnpj = "47.742.897/0001-46";
        final String invalidCnpj2 = "998.352.292-60";

        Assert.assertTrue(cnpj.validate(validCnpj));
        Assert.assertFalse(cnpj.validate(invalidCnpj));
        Assert.assertFalse(cnpj.validate(invalidCnpj2));

        Assert.assertFalse(cnpj.validate("00.000.000/0000.00"));
        Assert.assertFalse(cnpj.validate("11.111.111/1111.11"));
        Assert.assertFalse(cnpj.validate("22.222.222/2222.10"));
        Assert.assertFalse(cnpj.validate("22.222.222/2221.22"));
    }

    @Test
    public void tValidateLong() {
        final long validCnpj = 57742897000146L;
        final long invalidCnpj = 47742897000146L;

        Assert.assertTrue(cnpj.validate(validCnpj));
        Assert.assertFalse(cnpj.validate(invalidCnpj));
    }
}
