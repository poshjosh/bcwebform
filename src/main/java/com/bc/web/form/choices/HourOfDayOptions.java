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

package com.bc.web.form.choices;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 2:21:09 PM
 */
public class HourOfDayOptions implements Map<Integer, String>{

    private final Map<Integer, String> choices;

    public HourOfDayOptions() {
        final Map map = new HashMap(24, 1.0f);
        for(int i=0; i<24; i++) {
            final String suffix = i < 12 ? " AM" : i > 12 ? " PM" : " Noon";
            map.put(i, i + suffix);
        }
        choices = Collections.unmodifiableMap(map);
    }

    @Override
    public int size() {
        return choices.size();
    }

    @Override
    public boolean isEmpty() {
        return choices.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return choices.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return choices.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return choices.get(key);
    }

    @Override
    public String put(Integer key, String value) {
        return choices.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return choices.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        choices.putAll(m);
    }

    @Override
    public void clear() {
        choices.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return choices.keySet();
    }

    @Override
    public Collection<String> values() {
        return choices.values();
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return choices.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return choices.equals(o);
    }

    @Override
    public int hashCode() {
        return choices.hashCode();
    }

    @Override
    public String getOrDefault(Object key, String defaultValue) {
        return choices.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super Integer, ? super String> action) {
        choices.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super Integer, ? super String, ? extends String> function) {
        choices.replaceAll(function);
    }

    @Override
    public String putIfAbsent(Integer key, String value) {
        return choices.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return choices.remove(key, value);
    }

    @Override
    public boolean replace(Integer key, String oldValue, String newValue) {
        return choices.replace(key, oldValue, newValue);
    }

    @Override
    public String replace(Integer key, String value) {
        return choices.replace(key, value);
    }

    @Override
    public String computeIfAbsent(Integer key, Function<? super Integer, ? extends String> mappingFunction) {
        return choices.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public String computeIfPresent(Integer key, BiFunction<? super Integer, ? super String, ? extends String> remappingFunction) {
        return choices.computeIfPresent(key, remappingFunction);
    }

    @Override
    public String compute(Integer key, BiFunction<? super Integer, ? super String, ? extends String> remappingFunction) {
        return choices.compute(key, remappingFunction);
    }

    @Override
    public String merge(Integer key, String value, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return choices.merge(key, value, remappingFunction);
    }
}
