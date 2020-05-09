/*
 * Copyright 2019 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bc.webform.functions;

import java.util.function.UnaryOperator;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 26, 2019 10:55:40 AM
 */
public class LabelFromName implements UnaryOperator<String> {

    @Override
    public String apply(String name) {
        final StringBuilder b = new StringBuilder();
        for(int i=0; i<name.length(); i++) {
            final char ch = name.charAt(i);
            if(i == 0) {
                b.append(Character.toTitleCase(ch));
            }else{
                if(ch == '-' || ch == '_') {
                    b.append(' ');
                }else if(Character.isUpperCase(ch) || Character.isTitleCase(ch)) {
                    b.append(' ').append(ch);
                }else{
                    b.append(ch);
                }
            }
        }
        return b.toString();
    }
}
