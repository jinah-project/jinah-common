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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.obadaro.jinah.common.util.reflection.FieldAccess;
import com.obadaro.jinah.common.util.reflection.Reflections;

/**
 * 
 * @author Roberto Badaro
 */
public class ReflectionsTest {

    @Test
    public void tFindFields() {

        FieldAccess[] fa = Reflections.findFields(AnyData.class, new String[] { "id", "name" });

        Assert.assertTrue(fa != null);
        Assert.assertTrue(fa.length == 2);
    }

    @Test
    public void tFindMethod() {

        Method m = Reflections.findMethod(AnyData.class, "getId", new Class[0]);
        Assert.assertTrue(m != null);
    }

    @Test
    public void tFindMethodInAncestor() {

        Method m = Reflections.findMethod(AnotherData.class, "getId", new Class[0]);
        Assert.assertTrue(m != null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void fFindFieldsByAnnotation() {

        Class<?>[] annotations = new Class<?>[]{Id.class, EmbeddedId.class};

        FieldAccess[] fa =
                Reflections.findFieldsByAnnotation(AnyData.class, (Class<? extends Annotation>[]) annotations);

        Assert.assertTrue(fa != null);
        Assert.assertTrue(fa.length == 2);
    }

    @Test
    public void tFieldAccessDirectAccess() {

        FieldAccess[] fa = Reflections.findFields(AnotherData.class, new String[] { "name" });

        Assert.assertTrue(fa != null);
        Assert.assertTrue(fa.length == 1);

        final FieldAccess f = fa[0];
        final AnotherData data = new AnotherData();
        final String name = "Um nome qualquer";
        final String otherName = "Um nome qualquer";

        data.setName(name);

        String nomeRecuperado = (String) f.get(data);
        Assert.assertTrue(name.equals(nomeRecuperado));

        f.set(data, otherName);
        Assert.assertTrue(otherName.equals(data.getName()));
        Assert.assertTrue(f.toString() != null);
    }

    @Test
    public void tFieldAccessByMethodAccess() throws Exception {

        FieldAccess fa = new FieldAccess(AnyData.class, AnyData.class.getDeclaredField("name"), false);

        Assert.assertTrue(fa != null);

        final AnyData data = new AnyData();
        final String name = "Um nome qualquer";
        final String otherName = "Um nome qualquer";

        data.setName(name);

        String returnedName = (String) fa.get(data);
        Assert.assertTrue(name.equals(returnedName));

        fa.set(data, otherName);
        Assert.assertTrue(otherName.equals(data.getName()));
    }

    @Test
    public void tFieldAccessByMethodAccessBoolean() throws Exception {

        FieldAccess fa = new FieldAccess(AnyData.class, AnyData.class.getDeclaredField("active"), false);

        final AnyData data = new AnyData();
        data.setActive(true);

        boolean active = (Boolean) fa.get(data);
        Assert.assertTrue(data.isActive() == active);

        fa.set(data, false);
        Assert.assertTrue(false == data.isActive());
    }

    @Test(expected = Exception.class)
    public void tFieldAccessError() throws Exception {

        FieldAccess fa =
                new FieldAccess(AnyData.class, AnotherData.class.getDeclaredField("otherData"), false);

        final AnyData data = new AnyData();
        data.setActive(true);

        boolean active = (Boolean) fa.get(data);
        Assert.assertTrue(data.isActive() == active);

        fa.set(data, false);
        Assert.assertTrue(false == data.isActive());
    }

    @Test(expected = IllegalArgumentException.class)
    public void tFindFieldsByAnnotationWithError() {

        Reflections.findFieldsByAnnotation(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fNewInstanceWithError() {

        Reflections.newInstance(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tFindMethodWithError() {

        Reflections.findMethod((Class<?>) null, (Class<?>) null, "", (Class<?>[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tFindFieldsWithError() {

        Reflections.findFields(null, null, null);
    }

    // ------------------------------------------------------------------------------
    // Inner classes
    // ------------------------------------------------------------------------------

    @Target({ TYPE })
    @Retention(RUNTIME)
    @interface MappedSuperclass {
    }

    @Target({ ElementType.FIELD, ElementType.METHOD })
    @Retention(RUNTIME)
    @interface Id {
    }

    @Target({ ElementType.FIELD, ElementType.METHOD })
    @Retention(RUNTIME)
    @interface EmbeddedId {
    }

    @Target({ TYPE })
    @Retention(RUNTIME)
    @interface Embeddable {
    }

    @Target({ ElementType.FIELD, ElementType.METHOD })
    @Retention(RUNTIME)
    @interface Column {
    }

    @MappedSuperclass
    public static class AnyData {
        @Id
        @Column
        private Long id;
        @Column
        private String name;
        @Column
        private Date date;
        @Column
        private boolean active = false;
        @EmbeddedId
        private CompositeKey compositeKey;

        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Date getDate() {
            return date;
        }
        public void setDate(Date date) {
            this.date = date;
        }
        public CompositeKey getCompositeKey() {
            return compositeKey;
        }
        public void setCompositeKey(CompositeKey compositeKey) {
            this.compositeKey = compositeKey;
        }
        public boolean isActive() {
            return active;
        }
        public void setActive(boolean active) {
            this.active = active;
        }
    }

    @MappedSuperclass
    public static class AnotherData extends AnyData {
        private String otherData;

        public String getOtherData() {
            return otherData;
        }
        public void setOtherData(String otherData) {
            this.otherData = otherData;
        }
    }

    @Embeddable
    public static class CompositeKey implements Serializable {

        private static final long serialVersionUID = 1L;

        @Column
        private String valueA;
        @Column
        private String valueB;

        public CompositeKey() {
            // noop
        }

        public CompositeKey(String valorA, String valorB) {
            valueA = valorA;
            valueB = valorB;
        }

        public String getValueA() {
            return valueA;
        }
        public void setValueA(String valueA) {
            this.valueA = valueA;
        }
        public String getValueB() {
            return valueB;
        }
        public void setValueB(String valueB) {
            this.valueB = valueB;
        }
    }

}
