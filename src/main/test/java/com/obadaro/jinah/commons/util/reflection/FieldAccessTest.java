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
package com.obadaro.jinah.commons.util.reflection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.obadaro.jinah.common.JinahException;
import com.obadaro.jinah.common.util.reflection.FieldAccess;

/**
 * 
 * @author Roberto Badaro
 */
public class FieldAccessTest {

    private FieldAccess fieldAccess;

    @Before
    public void setup() {
        fieldAccess = new FieldAccess();
    }

    @Test
    public void get() {
        fieldAccess = new FieldAccess(Boolean.FALSE, null);
        Object source = new Object();
        Object retorno = new Object();
        retorno = fieldAccess.get(source);
        Assert.assertTrue(retorno == null);
    }

    @Test
    public void getTrue() {
        fieldAccess = new FieldAccess(Boolean.FALSE, null);
        Object source = null;
        Object retorno = new Object();
        retorno = fieldAccess.get(source);
        Assert.assertTrue(retorno == null);
    }

    @Test(expected = JinahException.class)
    public void getFaultException() {
        fieldAccess = new FieldAccess(Boolean.TRUE, null);
        Object source = new Object();
        new Object();
        fieldAccess.get(source);
    }
    @Test
    public void getSourceNulo() {
        fieldAccess = new FieldAccess(Boolean.FALSE, null);
        Object source = null;
        Object retorno = new Object();
        retorno = fieldAccess.get(source);
        Assert.assertTrue(retorno == null);
    }

    @Test
    public void getNulo() {
        fieldAccess = new FieldAccess(Boolean.FALSE, null);
        Object source = new Object();
        Object retorno = new Object();
        retorno = fieldAccess.get(source);
        Assert.assertTrue(retorno == null);
    }

    @Test
    public void set() {
        fieldAccess = new FieldAccess(Boolean.FALSE, null);
        Object source = new Object();
        fieldAccess.set(source, "");
        Assert.assertTrue(fieldAccess != null);
    }

    @Test(expected = JinahException.class)
    public void setTrue() {
        fieldAccess = new FieldAccess(Boolean.TRUE, null);
        Object source = new Object();
        fieldAccess.set(source, "");
    }

}
