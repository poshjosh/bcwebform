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

package com.bc.webform;

/**
 * @see https://www.w3schools.com/tags/tag_input.asp
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 12:44:59 AM
 */
public interface StandardFormFieldTypes {

    /**
     * @see https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/radio
     */
    String RADIO = "radio";
    
    /**
     * @see https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/checkbox
     */
    String CHECKBOX = "checkbox";
    
    /**
     * @see https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/date
     */
    String DATE = "date";
    
    /**
     * @see https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/time
     */
    String TIME = "time";
    
    /**
     * @see https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/datetime
     * @see https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/datetime-local
     */
    String DATETIME = "datetime-local";
    String FILE = "file";
    String HIDDEN = "hidden";
    String NUMBER = "number";
    String PASSWORD = "password";
    String TEXT = "text";
    
    default boolean isRadioType(String type) {
        return RADIO.equalsIgnoreCase(type);
    }
    
    default boolean isCheckboxType(String type) {
        return CHECKBOX.equalsIgnoreCase(type);
    }
    
    default boolean isDateType(String type) {
        return DATE.equalsIgnoreCase(type);
    }
    
    default boolean isDatetimeType(String type) {
        return DATETIME.equalsIgnoreCase(type);
    }
    
    default boolean isFileType(String type) {
        return FILE.equalsIgnoreCase(type);
    }
    
    default boolean isHidden(String type) {
        return HIDDEN.equalsIgnoreCase(type);
    }
    
    default boolean isNumberType(String type) {
        return NUMBER.equalsIgnoreCase(type);
    }
    
    default boolean isPasswordType(String type) {
        return PASSWORD.equalsIgnoreCase(type);
    }
    
    default boolean isTextType(String type) {
        return TEXT.equalsIgnoreCase(type);
    }

    default boolean isTimeType(String type) {
        return TIME.equalsIgnoreCase(type);
    }
}
