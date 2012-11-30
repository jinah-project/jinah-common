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
package com.obadaro.jinah.common.util.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.obadaro.jinah.common.JinahException;

/**
 * A {@link Field} wrapper to provide access by reflection.
 * 
 * @author Roberto Badaro
 */
public class FieldAccess {

    private Class<?> owner;
    private Field field;
    private Method getter;
    private Method setter;

    private boolean directAccess = true;

    public FieldAccess() {
        // NOOP
    }

    public FieldAccess(final boolean directAccess, final Method getter) {

        this.directAccess = directAccess;
        this.getter = getter;
    }

    /**
     * Configures FieldAccess.
     * 
     * @param owner
     *            Classe a que pertence o field.
     * @param field
     *            O field.
     */
    public FieldAccess(final Class<?> owner, final Field field) {

        this(owner, field, true);
    }

    /**
     * Configures FieldAccess.
     * 
     * @param owner
     *            Classe a que pertence o field.
     * @param field
     *            O field.
     * @param directAccess
     *            Acessa o valor do field diretamente ou usa get/set?
     */
    public FieldAccess(final Class<?> owner, final Field field, final boolean directAccess) {

        this.directAccess = directAccess;
        this.owner = owner;
        this.field = field;

        try {
            if (this.directAccess) {
                field.setAccessible(true);
                setter = null;
                getter = null;

            } else {
                final String fname = field.getName();
                final String baseName = fname.substring(0, 1).toUpperCase() + fname.substring(1);
                final Class<?> fieldType = field.getType();
                String pfxGet = "get";

                if (fieldType.isPrimitive() && boolean.class.isAssignableFrom(fieldType)) {
                    pfxGet = "is";
                }

                setter = Reflections.findMethod(owner, "set" + baseName, field.getType());
                getter = Reflections.findMethod(owner, pfxGet + baseName, new Class<?>[0]);

                if (setter == null && getter == null) {
                    throw new JinahException(String.format(
                        "Accessor methods (get|is/set) not found for '%s' on class '%s'.", field.getName(),
                        owner.getName()));
                }
            }
        } catch (final Exception e) {
            throw new JinahException(String.format("Error building FieldAccess for '%s' from class '%s'.",
                field, owner), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return new StringBuilder("FieldAccess[field=").append(field.getName())
                .append("][type=")
                .append(field.getType().getName())
                .append("]")
                .append("][owner=")
                .append(owner.getName())
                .append("]")
                .toString();
    }

    /**
     * Returns the wrapped field name. Same as <code>fieldAccess.getField().getName()</code>.
     * 
     * @return The wrapped field name
     */
    public String getName() {

        return field.getName();
    }

    /**
     * Returns the value of the field represented by this Field, on the specified object. The value
     * is automatically wrapped in an object if it has a primitive type.
     * 
     * @param source
     * @return the value of the field represented by this Field.
     */
    public Object get(final Object source) {

        if (!directAccess && (getter == null || source == null)) {
            return null;
        }

        try {
            if (directAccess) {
                return field.get(source);
            } else {
                return getter.invoke(source);
            }
        } catch (final Exception e) {
            throw new JinahException(
                String.format("Error getting field value. Field '%s'; class '%s'. Direct? %s.", field,
                    source, directAccess), e);
        }
    }

    /**
     * Sets the field represented by this Field object on the specified object argument to the
     * specified new value. The new value is automatically unwrapped if the underlying field has a
     * primitive type.
     * 
     * @param target
     * @param value
     */
    public void set(final Object target, final Object value) {

        if (!directAccess && setter == null) {
            return;
        }

        try {
            if (directAccess) {
                field.set(target, value);
            } else {
                setter.invoke(target, value);
            }
        } catch (final Exception e) {
            throw new JinahException(
                String.format("Error setting field value. Field '%s'; class '%s'. Direct? %s.", field,
                    target, directAccess), e);
        }
    }

    /**
     * Returns the declared class used to create the wrapper.
     * 
     * @return
     */
    public Class<?> getOwner() {

        return owner;
    }

    /**
     * Returns the wrapped field.
     * 
     * @return
     */
    public Field getField() {

        return field;
    }

}
