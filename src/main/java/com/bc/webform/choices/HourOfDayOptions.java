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

package com.bc.webform.choices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2019 2:21:09 PM
 * @see com.bc.webform.choices.HourOfDayOption
 */
public final class HourOfDayOptions{
    
    private HourOfDayOptions() { }

    public static final List<SelectOption<Integer>> get() {
        final int size = 24;
        final List<HourOfDayOption> options = new ArrayList<>(size);
        for(int i=0; i<size; i++) {
            options.add(new HourOfDayOption(i));
        }
        return Collections.unmodifiableList(options);
    }
}
