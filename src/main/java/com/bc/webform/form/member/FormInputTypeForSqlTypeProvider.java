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

package com.bc.webform.form.member;

import com.bc.webform.StandardFormFieldTypes;
import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 1:20:57 PM
 */
public class FormInputTypeForSqlTypeProvider implements Function<Integer, String>{

    private static final Logger LOG = Logger.getLogger(FormInputTypeForSqlTypeProvider.class.getName());

    private final String resultIfNone;

    public FormInputTypeForSqlTypeProvider() {
        this(StandardFormFieldTypes.TEXT);
    }
    
    public FormInputTypeForSqlTypeProvider(String resultIfNone) {
        this.resultIfNone = resultIfNone;
    }
    
    //@TODO consult specifications and confirm these values
    public Class getClass(int sqlType) {
        final Class result;
        switch(sqlType) {
            case Types.CHAR:
            case Types.NCHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.LONGNVARCHAR:
                 result = String.class; break;
            case Types.DATE: 
                result = LocalDateTime.class; break;
            case Types.TIMESTAMP:
                result = LocalDateTime.class; break;
            case Types.TIMESTAMP_WITH_TIMEZONE:
                result = ZonedDateTime.class; break;
            case Types.TIME:
                result = LocalTime.class; break;
            case Types.TIME_WITH_TIMEZONE:
                result = ZonedDateTime.class; break;
            case Types.BIT:
            case Types.BOOLEAN:
                result = Boolean.class; break;
//            case Types.TINYINT:    
            case Types.SMALLINT: 
                result = Short.class; break;
            case Types.INTEGER: 
                result = Integer.class; break;
            case Types.BIGINT: 
                result = Long.class; break;
            case Types.REAL: 
            case Types.DECIMAL: 
                result = BigDecimal.class; break;
            case Types.FLOAT: 
                result = Float.class; break;
            case Types.DOUBLE: 
                result = Double.class; break;
            case Types.NUMERIC:
                result = Number.class; break;
            default:
                result = Object.class;
        }
        
        LOG.finest(() -> "SQL Type: " + sqlType + ", output: " + result);
        
        return result;
    }
    
    @Override
    public String apply(Integer sqlType) {
        final String result;
        switch(sqlType) {
            case Types.CHAR:
            case Types.NCHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.LONGNVARCHAR:
                 result = StandardFormFieldTypes.TEXT; break;
            case Types.DATE: 
                result = StandardFormFieldTypes.DATE; break;
            case Types.TIMESTAMP:
            case Types.TIMESTAMP_WITH_TIMEZONE:
                result = StandardFormFieldTypes.DATETIME; break;
            case Types.TIME:
            case Types.TIME_WITH_TIMEZONE:
                result = StandardFormFieldTypes.TIME; break;
            case Types.BIT:
            case Types.BOOLEAN:
                result = StandardFormFieldTypes.CHECKBOX; break;
//            case Types.TINYINT:    
            case Types.SMALLINT: 
            case Types.INTEGER: 
            case Types.BIGINT: 
            case Types.REAL: 
            case Types.FLOAT: 
            case Types.DOUBLE: 
            case Types.DECIMAL: 
            case Types.NUMERIC:
                result = StandardFormFieldTypes.NUMBER; break;
            default:
                result = resultIfNone;
        }
        
        LOG.finest(() -> "SQL Type: " + sqlType + ", output: " + result);
        
        return result;
    }

    public String getResultIfNone() {
        return resultIfNone;
    }
}
