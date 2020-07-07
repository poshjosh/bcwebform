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

import java.util.List;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2019 2:33:28 PM
 */
public interface TableMetadata {

    int getColumnDataType(String name);

    int getColumnDisplaySize(String name);

    int getColumnNullable(String name);

    boolean isAutoIncrement(String name);

    boolean isGeneratedColumn(String name);

    List<Integer> getColumnDataTypes();

    List<Integer> getColumnDiplaySizes();

    List<String> getColumnNames();

    List<Integer> getColumnNullables();

    List<String> getIsAutoIncrement();

    List<String> getIsGeneratedColumn();
}
