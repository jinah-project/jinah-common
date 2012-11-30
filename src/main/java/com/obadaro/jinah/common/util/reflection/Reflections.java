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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.obadaro.jinah.common.JinahException;
import com.obadaro.jinah.common.util.Preconditions;
import com.obadaro.jinah.common.util.Strings;

/**
 * 
 * @author Roberto Badaro
 */
public class Reflections {

    /**
     * Retrieves the fields that have the provided {@code annotations}. Also
     * searches in the superclasses.
     * 
     * @param clazz
     *            Class where to search
     * @param annotations
     *            One or more annotations to search
     * @return {@link FieldAccess} array with found fields.
     */
    public static FieldAccess[] findFieldsByAnnotation(final Class<?> clazz,
                                                       final Class<? extends Annotation>[] annotations) {

        return findFieldsByAnnotation(clazz, null, annotations);
    }

    /**
     * Retrieves the fields that have the provided {@code annotations}. Also
     * searches in the superclasses.
     * 
     * @param clazz
     *            Class where to search.
     * @param stopClass
     *            Limit to search on ascendant hierarchy.
     * @param annotations
     *            One or more annotations to search
     * @return {@link FieldAccess} array with found fields.
     */
    public static FieldAccess[] findFieldsByAnnotation(final Class<?> clazz,
                                                       Class<?> stopClass,
                                                       final Class<? extends Annotation>[] annotations) {

        Preconditions.checkArgument(clazz != null, "clazz");
        Preconditions.checkArgument(annotations != null && annotations.length > 0, "annotations");

        if (stopClass == null) {
            stopClass = Object.class;
        }

        Class<?> cls = clazz;
        final List<FieldAccess> l = new ArrayList<FieldAccess>();

        while (cls != null) {

            final Field[] fields = cls.getDeclaredFields();

            for (final Field f : fields) {
                for (final Class<? extends Annotation> a : annotations) {
                    if (f.isAnnotationPresent(a)) {
                        l.add(new FieldAccess(clazz, f));
                        break;
                    }
                }
            }

            if (!cls.equals(stopClass)) {
                cls = cls.getSuperclass();
            } else {
                break;
            }
        }

        final FieldAccess[] fieldsEncontrados = l.toArray(new FieldAccess[0]);
        return fieldsEncontrados;
    }

    /**
     * Retrieves the methods that have the provided {@code annotations}. Also
     * searches in the superclasses.
     * 
     * @param clazz
     *            Class where to search.
     * @param stopClass
     *            Limit to search on ascendant hierarchy.
     * @param annotations
     *            One or more annotations to search
     * @return {@link Method} array with found methods.
     */
    public static Method[] findMethodsByAnnotation(final Class<?> clazz,
                                                   Class<?> stopClass,
                                                   final Class<? extends Annotation>[] annotations) {

        Preconditions.checkArgument(clazz != null, "clazz");
        Preconditions.checkArgument(annotations != null && annotations.length > 0, "annotations");

        if (stopClass == null) {
            stopClass = Object.class;
        }

        Class<?> cls = clazz;
        final List<Method> l = new ArrayList<Method>();

        while (cls != null) {

            final Method[] methods = cls.getDeclaredMethods();

            for (final Method m : methods) {
                for (final Class<? extends Annotation> a : annotations) {
                    if (m.isAnnotationPresent(a)) {
                        l.add(m);
                        break;
                    }
                }
            }

            if (!cls.equals(stopClass)) {
                cls = cls.getSuperclass();
            } else {
                break;
            }
        }

        final Method[] methodsEncontrados = l.toArray(new Method[0]);
        return methodsEncontrados;
    }

    /**
     * Creates a new instance. Just surround the {@code Class#newInstance()} and
     * throws {@code JinahException} if it fails.
     * 
     * @param <T>
     *            Return type.
     * @param clazz
     *            Class to create a new Object.
     * @return
     * @throws JinahException
     *             If fails.
     */
    public static <T> T newInstance(final Class<T> clazz) {

        Preconditions.checkArgument(clazz != null, "clazz");

        try {
            return clazz.newInstance();

        } catch (final Exception e) {
            throw new JinahException(String.format("Class.newInstance() failed for class '%s'. Message: %s",
                clazz,
                e.getMessage()),
                e);
        }
    }

    /**
     * Retrieves the method, if exists. Also searches in the superclasses.
     * 
     * @param clazz
     *            Class where to search.
     * @param methodName
     *            Method name
     * @param args
     *            Arguments in method signature. If method signature have no
     *            arguments, inform {@code new Class<?>[0]}.
     * @return Found method, or {@code null} if method not found.
     */
    public static Method findMethod(final Class<?> clazz,
                                    final String methodName,
                                    final Class<?>... args) {

        return findMethod(clazz, null, methodName, args);
    }

    /**
     * Retrieves the method, if exists. Also searches in the superclasses.
     * 
     * @param clazz
     *            Class where to search.
     * @param stopClass
     *            Limit to search on ascendant hierarchy.
     * @param methodName
     *            Method name
     * @param args
     *            Arguments in method signature. If method signature have no
     *            arguments, inform {@code new Class<?>[0]}.
     * @return Found method, or {@code null} if method not found.
     */
    public static Method findMethod(final Class<?> clazz,
                                    Class<?> stopClass,
                                    final String methodName,
                                    final Class<?>... args) {

        Preconditions.checkArgument(clazz != null, "clazz");
        Preconditions.checkArgument(Strings.isNotBlank(methodName), "methodName");
        Preconditions.checkArgument(args != null, "args");

        if (stopClass == null) {
            stopClass = Object.class;
        }

        Method method = null;
        Class<?> cls = clazz;

        while (cls != null) {
            try {
                method = cls.getDeclaredMethod(methodName, args);
                break;
            } catch (final NoSuchMethodException e) {
                if (!cls.equals(stopClass)) {
                    cls = cls.getSuperclass();
                } else {
                    break;
                }
            }
        }

        return method;
    }

    /**
     * Retrieves the fields that have the provided names. Also searches in the superclasses.
     * 
     * @param clazz
     *            Class where to search.
     * @param names
     *            Field names to search.
     * @return Found fields. If no fields found, returns an empty array.
     */
    public static FieldAccess[] findFields(final Class<?> clazz, final String[] names) {

        return findFields(clazz, null, names);
    }

    /**
     * Retrieves the fields that have the provided names. Also searches in the superclasses.
     * 
     * @param clazz
     *            Class where to search.
     * @param stopClass
     *            Field names to search.
     * @param names
     *            Field names to search.
     * @return Found fields. If no fields found, returns an empty array.
     */
    public static FieldAccess[] findFields(final Class<?> clazz,
                                           Class<?> stopClass,
                                           final String[] names) {

        Preconditions.checkArgument(clazz != null, "clazz");
        Preconditions.checkArgument(names != null && names.length > 0, "names");

        if (stopClass == null) {
            stopClass = Object.class;
        }

        final List<FieldAccess> l = new ArrayList<FieldAccess>();

        for (final String nome : names) {
            Class<?> cls = clazz;
            while (cls != null) {
                try {
                    final Field field = cls.getDeclaredField(nome);
                    l.add(new FieldAccess(clazz, field));
                    break;
                } catch (final NoSuchFieldException e) {
                    if (!cls.equals(stopClass)) {
                        cls = cls.getSuperclass();
                    } else {
                        break;
                    }
                }
            }
        }

        final FieldAccess[] fields = l.toArray(new FieldAccess[0]);
        return fields;
    }

    /**
     * Returns the fields where type is compatible with {@code classOrInterface}. Also searches in
     * the superclasses.
     * 
     * @param clazz
     *            Class where to search.
     * @param stopClass
     *            Limit to search on ascendant hierarchy.
     * @param classOrInterface
     *            Class or Interface to compare.
     * @return Found fields. If no fields found, returns an empty array.
     */
    public static FieldAccess[] findCompatibleFields(final Class<?> clazz,
                                                     Class<?> stopClass,
                                                     final Class<?> classOrInterface) {

        Preconditions.checkArgument(clazz != null, "clazz");
        Preconditions.checkArgument(classOrInterface != null, "classOrInterface");

        if (stopClass == null) {
            stopClass = Object.class;
        }

        final List<FieldAccess> l = new ArrayList<FieldAccess>();

        Class<?> cls = clazz;
        while (cls != null) {
            final Field[] fields = cls.getDeclaredFields();
            for (final Field field : fields) {
                if (classOrInterface.isAssignableFrom(field.getType())) {
                    l.add(new FieldAccess(clazz, field));
                }
            }

            if (!cls.equals(stopClass)) {
                cls = cls.getSuperclass();
            } else {
                break;
            }
        }

        final FieldAccess[] fields = l.toArray(new FieldAccess[0]);
        return fields;
    }

    /**
     * Retrieves the top class that have the provided annotation.
     * 
     * @param baseClass
     * @param annotation
     * @return The top superclass found with the annotation, or {@code null} if not found.
     */
    public static Class<?> findTopClassByAnnotation(final Class<?> baseClass,
                                                    final Class<? extends Annotation> annotation) {

        Preconditions.checkArgument(baseClass != null, "baseClass");
        Preconditions.checkArgument(annotation != null, "annotation");

        Class<?> topClass = null;
        Class<?> superClass = baseClass.getSuperclass();

        while (superClass != null) {
            if (superClass.isAnnotationPresent(annotation)) {
                topClass = superClass;
                superClass = superClass.getSuperclass();
            } else {
                break;
            }
        }

        return topClass;
    }

}
