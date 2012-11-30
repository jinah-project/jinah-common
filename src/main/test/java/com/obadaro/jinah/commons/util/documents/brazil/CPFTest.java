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

import com.obadaro.jinah.common.util.documents.brazil.CPF;

/**
 * 
 * @author Roberto Badaro
 */
public class CPFTest {

    private final CPF cpf = new CPF();

    @Test
    public void tMask() {
        Assert.assertTrue("111.111.111-11".equals(cpf.mask("11111111111")));
        Assert.assertTrue("011.111.111-11".equals(cpf.mask("1111111111")));
    }

    @Test
    public void tFormat() {
        Assert.assertTrue("111.111.111-11".equals(cpf.format(11111111111L)));
        Assert.assertTrue("011.111.111-11".equals(cpf.format(1111111111L)));
    }

    @Test
    public void tValidateString() {
        final String validCpf = "998.352.292-60";
        final String invalidCpf = "998.352.291-60";

        Assert.assertTrue(cpf.validate(validCpf));
        Assert.assertFalse(cpf.validate(invalidCpf));
    }

    @Test
    public void tValidateLong() {
        final long validCpf = 99835229260L;
        final long invalidCpf = 99835229160L;

        Assert.assertTrue(cpf.validate(validCpf));
        Assert.assertFalse(cpf.validate(invalidCpf));
    }
}
