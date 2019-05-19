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

package com.bc.web.form.functions;

import java.util.function.Predicate;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2019 2:02:56 PM
 */
public class IsPasswordField implements Predicate<String>{

    @Override
    public boolean test(String fieldName) {
        return ("password".equalsIgnoreCase(fieldName) || (fieldName != null && fieldName.toLowerCase().contains("password")));
    }
}
