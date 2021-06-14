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

import com.bc.webform.TableMetadataImpl;
import com.bc.webform.TableMetadata;
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
public class IsFormFieldTestForDatabaseTable implements BiPredicate<String, String>{
    
    private final WeakHashMap<TableMetadata, String> cache;
    
    private final MetaDataAccess metaDataAccess;
    
    public IsFormFieldTestForDatabaseTable(EntityManagerFactory emf) {
        this(new MetaDataAccessImpl(emf));
    }
    public IsFormFieldTestForDatabaseTable(MetaDataAccess dbMetaDataAccess) {
        this.cache = new WeakHashMap();
        this.metaDataAccess = Objects.requireNonNull(dbMetaDataAccess);
    }
    @Override
    public boolean test(String tableName, String columnName) {
        final TableMetadata tableMetadata = this.getOrCreateMetadata(tableName);
        return ! (tableMetadata.isAutoIncrement(columnName) || 
                tableMetadata.isGeneratedColumn(columnName));
    }
    
    public TableMetadata getOrCreateMetadata(String tableName) {
        TableMetadata tmeta = this.getMetadataFromCache(tableName);
        if(tmeta == null) {
            tmeta = new TableMetadataImpl(metaDataAccess, null, null, tableName);
            cache.put(tmeta, tableName);
        }
        return tmeta;
    }
    
    public TableMetadata getMetadataFromCache(String tableName) {
        TableMetadata tmeta = null;
        for(Map.Entry<TableMetadata, String> entry : cache.entrySet()) {
            if(entry.getValue().equals(tableName)) {
                tmeta = entry.getKey();
                break;
            }
        }
        return tmeta;
    }
}
