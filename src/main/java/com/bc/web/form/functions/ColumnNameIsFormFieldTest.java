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

package com.bc.web.form.functions;

import com.bc.db.meta.access.MetaDataAccess;
import com.bc.db.meta.access.MetaDataAccessImpl;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.BiPredicate;
import javax.persistence.EntityManagerFactory;

/**
 * @author Chinomso Bassey Ikwuagwu on May 8, 2020 4:37:42 PM
 */
public class ColumnNameIsFormFieldTest implements BiPredicate<String, String>{
    
    private final WeakHashMap<TableMetadata, String> tableMetadata;
    
    private final MetaDataAccess dbMetaDataAccess;
    
    public ColumnNameIsFormFieldTest(EntityManagerFactory emf) {
        this(new MetaDataAccessImpl(emf));
    }
    public ColumnNameIsFormFieldTest(MetaDataAccess dbMetaDataAccess) {
        this.tableMetadata = new WeakHashMap();
        this.dbMetaDataAccess = Objects.requireNonNull(dbMetaDataAccess);
    }
    @Override
    public boolean test(String table, String columnName) {
        TableMetadata tmeta = null;
        for(Map.Entry<TableMetadata, String> entry : tableMetadata.entrySet()) {
            if(entry.getValue().equals(table)) {
                tmeta = entry.getKey();
                break;
            }
        }
        if(tmeta == null) {
            tmeta = new TableMetadataImpl(dbMetaDataAccess, null, null, table);
            tableMetadata.put(tmeta, table);
        }
        return ! (tmeta.isAutoIncrement(columnName) || tmeta.isGeneratedColumn(columnName));
    }
}
