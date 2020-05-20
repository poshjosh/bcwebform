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

import com.bc.webform.StandardFormFieldTypes;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.bc.webform.FormMember;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 2:38:34 PM
 */
public class FormMemberAdviceProvider implements Function<FormMember, String> {

    private final String resultIfNone;

    public FormMemberAdviceProvider() {
        this(null);
    }
    
    public FormMemberAdviceProvider(String resultIfNone) {
        this.resultIfNone = resultIfNone;
    }
    
    @Override
    public String apply(FormMember formField) {
        final String result;
        if(formField.isMultiChoice()) {
            result = formField.getChoices().values().stream().limit(3).collect(Collectors.joining("", ", ", "... etc")).toString();
        }else if(StandardFormFieldTypes.DATE.equalsIgnoreCase(formField.getType()) && !formField.getForm().getDatePatterns().isEmpty()) {
            result = formField.getForm().getDatePatterns().get(0);
        }else if(StandardFormFieldTypes.DATETIME.equalsIgnoreCase(formField.getType()) && !formField.getForm().getDatetimePatterns().isEmpty()) {
            result = formField.getForm().getDatetimePatterns().get(0);
        }else{
            result = null;
        }
        return result == null ? resultIfNone : result;
    }
}
