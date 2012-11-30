/* 
 * JINAH Project - Java Is Not A Hammer
 * http://obadaro.com/jinah
 *
 * Copyright 2010-2012 Roberto Badaro 
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
package com.obadaro.jinah.common.internals;

import java.util.logging.Level;

/**
 * Internal logger class.
 * 
 * @author Roberto Badaro
 */
public class Logger {

    java.util.logging.Logger delegate;

    public static Logger getLogger(String name) {
        return new Logger(java.util.logging.Logger.getLogger(name));
    }

    public Logger(java.util.logging.Logger delegate) {
        this.delegate = delegate;
    }

    public void debug(String msg, Object... args) {
        delegate.log(Level.FINE, msg, args);
    }

    public void debug(Throwable throwable, String msg) {
        delegate.log(Level.FINE, msg, throwable);
    }

    public void info(String msg, Object... args) {
        delegate.log(Level.INFO, msg, args);
    }

    public void info(Throwable throwable, String msg) {
        delegate.log(Level.INFO, msg, throwable);
    }

    public void warn(String msg, Object... args) {
        delegate.log(Level.WARNING, msg, args);
    }

    public void warn(Throwable throwable, String msg) {
        delegate.log(Level.WARNING, msg, throwable);
    }

    public void error(String msg, Object... args) {
        delegate.log(Level.SEVERE, msg, args);
    }

    public void error(Throwable throwable, String msg) {
        delegate.log(Level.SEVERE, msg, throwable);
    }

}
