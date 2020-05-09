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
import java.sql.Types;
import java.util.Objects;
import java.util.function.Predicate;
import javax.persistence.EntityManagerFactory;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2019 2:25:42 PM
 */
public class TableColumnNameIsFormFieldTest implements Predicate<String> {

//    private static final Logger LOG = Logger.getLogger(TableColumnNameIsFormFieldTest.class.getName());

    private final TableMetadata tableMetadata;
    
    public TableColumnNameIsFormFieldTest(EntityManagerFactory emf, String table) {
        this(new MetaDataAccessImpl(emf), table);
    }
    
    public TableColumnNameIsFormFieldTest(MetaDataAccess mda, String table) {
        this(mda, null, null, table);
    }
    
    public TableColumnNameIsFormFieldTest(MetaDataAccess metaDataAccess, 
            String catalog, String schema, String table) {
        this(new TableMetadataImpl(metaDataAccess, catalog, schema, table));
    }
    
    public TableColumnNameIsFormFieldTest(TableMetadata tableMetadata) {
        this.tableMetadata = Objects.requireNonNull(tableMetadata);
    }

    @Override
    public boolean test(String columnName) {
        return !(this.tableMetadata.isAutoIncrement(columnName) ||  
                this.tableMetadata.isGeneratedColumn(columnName) ||
                this.tableMetadata.getColumnDataType(columnName) == Types.TIMESTAMP);
    }
}
