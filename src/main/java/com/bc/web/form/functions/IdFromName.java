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

import java.util.function.UnaryOperator;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 4:23:13 PM
 */
public class IdFromName implements UnaryOperator<String> {

    @Override
    public String apply(String name) {
//@todo check if some old code in javascript files depends on this form of id
//@todo ensure nothing depends on this format of id and then change to something else
//        return name + "Id";
        return name;
    }
}
