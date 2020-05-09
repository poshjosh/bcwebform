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
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 6:24:36 PM
 */
public class GetFormFieldTypeForField implements Function<Field, String>{

    private static final Logger LOG = Logger.getLogger(GetFormFieldTypeForField.class.getName());

    private final String resultIfNone;

    public GetFormFieldTypeForField() {
        this(StandardFormFieldTypes.TEXT);
    }
    
    public GetFormFieldTypeForField(String resultIfNone) {
        this.resultIfNone = resultIfNone;
    }
    
    @Override
    public String apply(Field field) {
        final String result;
        final Class cls = field.getType();
        if(CharSequence.class.isAssignableFrom(cls)) {
            result = StandardFormFieldTypes.TEXT;
        }else if(cls == Boolean.class || cls == boolean.class) {
            result = StandardFormFieldTypes.CHECKBOX;
        }else if(LocalTime.class.isAssignableFrom(cls) || 
                OffsetTime.class.isAssignableFrom(cls) ||
                java.sql.Time.class.isAssignableFrom(cls)) {
            result = StandardFormFieldTypes.TIME;
        }else if(java.time.ZonedDateTime.class.isAssignableFrom(cls) ||
                java.time.LocalDateTime.class.isAssignableFrom(cls) ||
                java.time.OffsetDateTime.class.isAssignableFrom(cls) ||
                java.sql.Timestamp.class.isAssignableFrom(cls)){
            result = StandardFormFieldTypes.DATETIME;
        }else if(java.util.Date.class.isAssignableFrom(cls) ||
                java.time.LocalDate.class.isAssignableFrom(cls)){
            result = StandardFormFieldTypes.DATE;
        }else if(Number.class.isAssignableFrom(cls)) {
            result = StandardFormFieldTypes.NUMBER;
        }else{
            result = null;
        }
        
        LOG.finer(() -> "Input: " + cls + ", output: " + result);
        
        return result == null ? resultIfNone : result;
    }
}
