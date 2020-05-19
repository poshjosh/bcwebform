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

import com.bc.webform.functions.IdFromName;
import com.bc.webform.functions.LabelFromName;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 4:57:25 PM
 */
public class DefaultForm implements Form {

    private final Form parent;
    private final String id;
    private final String name;
    private final String displayName;
    private final List<String> datePatterns;
    private final List<String> timePatterns;
    private final List<String> datetimePatterns;

    public DefaultForm(String name) {
        this(new IdFromName().apply(name), name, new LabelFromName().apply(name), 
                Arrays.asList("yyyy-MM-dd", "MM/dd/yyyy", "MM dd yyyy", "MM-dd-yyyy"), 
                Arrays.asList("HH:mm", "HH:mm:ss"),
                Arrays.asList("yyyy-MM-dd'T'HH:mm", "MM/dd/yyyy HH:mm", "MM dd yyyy HH:mm", "MM-dd-yyyy HH:mm",
                        "yyyy-MM-dd'T'HH:mm:ss", "MM/dd/yyyy HH:mm:ss", "MM dd yyyy HH:mm:ss", "MM-dd-yyyy HH:mm:ss",
                        "EEE MMM dd kk:mm:ss yyyy"));//"EEE MMM dd kk:mm:ss z yyyy"));
    }

    public DefaultForm(String id, String name, String displayName, List<String> datePatterns, List<String> timePatterns, List<String> datetimePatterns) {
        this.parent = null;
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.displayName = Objects.requireNonNull(displayName);
        this.datePatterns = Collections.unmodifiableList(datePatterns);
        this.timePatterns = Collections.unmodifiableList(timePatterns);
        this.datetimePatterns = Collections.unmodifiableList(datetimePatterns);
    }

    @Override
    public Optional<FormField> getFormField(String name) {
        return Optional.empty();
    }

    @Override
    public List<String> getFieldNames() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> getRequiredFieldNames() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> getOptionalFieldNames() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> getFileFieldNames() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<FormField> getFormFields() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Form getParent() {
        return parent;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLabel() {
        return displayName;
    }

    @Override
    public List<String> getDatePatterns() {
        return datePatterns;
    }

    @Override
    public List<String> getTimePatterns() {
        return timePatterns;
    }

    @Override
    public List<String> getDatetimePatterns() {
        return datetimePatterns;
    }
}
