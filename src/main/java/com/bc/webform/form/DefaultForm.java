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

package com.bc.webform.form;

import com.bc.webform.functions.IdFromName;
import com.bc.webform.functions.LabelFromName;
import java.util.Collections;
import java.util.Objects;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 4:57:25 PM
 */
public class DefaultForm<S> extends FormBean<S> {

    public DefaultForm(String name) {
        this(new IdFromName().apply(name), name, new LabelFromName().apply(name));
    }

    public DefaultForm(String id, String name, String label) {
        this.setId(Objects.requireNonNull(id));
        this.setName(Objects.requireNonNull(name));
        this.setLabel(Objects.requireNonNull(label));
        this.setMembers(Collections.EMPTY_LIST);
    }
}
