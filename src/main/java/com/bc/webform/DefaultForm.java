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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 4:57:25 PM
 */
public class DefaultForm<S> implements Form<S> {

    private final Form parent;
    private final String id;
    private final String name;
    private final String displayName;

    public DefaultForm(String name) {
        this(new IdFromName().apply(name), name, new LabelFromName().apply(name));
    }

    public DefaultForm(String id, String name, String displayName) {
        this.parent = null;
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.displayName = Objects.requireNonNull(displayName);
    }

    @Override
    public Form copy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FormBean writableCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<FormMember> getMemberOptional(String name) {
        return Optional.empty();
    }

    @Override
    public List<String> getMemberNames() {
        return this.getMembers().stream()
                .map((ff) -> ff.getName())
                .collect(Collectors.toList());
    }

    @Override
    public List<FormMember> getMembers() {
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
    public S getDataSource() {
        return null;
    }
}
