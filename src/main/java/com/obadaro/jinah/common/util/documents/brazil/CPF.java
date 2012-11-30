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
 * CPF - Cadastro de Pessoas Físicas.<br>
 * Pattern: {@code 999.999.999-99}.
 * 
 * @author Roberto Badaro
 */
public class CPF extends Metodo11ValidableDocument {

    public static final String MASK = "@@@.@@@.@@@-@@";
    public static final int DOC_LENGTH = 11;
    private static final int[] validationDigits = new int[] { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

    public static boolean isValid(long cpf) {
        return Metodo11.isValid(cpf, validationDigits);
    }

    public CPF() {
        super(MASK, DOC_LENGTH, validationDigits);
    }

}