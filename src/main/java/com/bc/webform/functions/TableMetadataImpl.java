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

import com.bc.db.meta.access.MetaDataAccess;
import com.bc.db.meta.access.MetaDataAccessImpl;
import com.bc.db.meta.access.SqlRuntimeException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2019 2:35:53 PM
 */
public class TableMetadataImpl implements TableMetadata {

    private static final Logger LOG = Logger.getLogger(TableMetadataImpl.class.getName());

    private final MetaDataAccess metaDataAcces;
    private final List<String> columnNames;
    private final List<Integer> columnNullables;
    private final List<Integer> columnDataTypes;
    private final List<Integer> columnDiplaySizes;
    private final List<String> isAutoIncrement;
    private final List<String> isGeneratedColumn;
    
    public TableMetadataImpl(EntityManagerFactory emf, String table) {
        this(new MetaDataAccessImpl(emf), table);
    }
    
    public TableMetadataImpl(MetaDataAccess mda, String table) {
        this(mda, null, null, table);
    }
    
    public TableMetadataImpl(MetaDataAccess metaDataAccess, 
            String catalog, String schema, String table) {
        this.metaDataAcces = Objects.requireNonNull(metaDataAccess);
        this.columnNames = metaDataAccess.fetchStringMetaData(catalog, schema, table, null, MetaDataAccess.COLUMN_NAME);
        this.columnNullables = metaDataAccess.fetchIntMetaData(catalog, schema, table, null, MetaDataAccess.COLUMN_NULLABLE);
        this.columnDataTypes = metaDataAccess.fetchIntMetaData(catalog, schema, table, null, MetaDataAccess.COLUMN_DATA_TYPE);
        this.columnDiplaySizes = metaDataAccess.fetchIntMetaData(catalog, schema, table, null, MetaDataAccess.COLUMN_SIZE);
        this.isAutoIncrement = fetchStringMetaDataWhichMayFail(
                catalog, schema, table, MetaDataAccess.IS_AUTOINCREMENT, "");
        this.isGeneratedColumn = fetchStringMetaDataWhichMayFail(
                catalog, schema, table, MetaDataAccess.IS_GENERATEDCOLUMN, "");
    }
    
    private List<String> fetchStringMetaDataWhichMayFail(String catalog, 
            String schema, String table, int n, String defaultValue) {
        try{
            return this.metaDataAcces.fetchStringMetaData(catalog, schema, table, null, n);
        }catch(SqlRuntimeException e) {
            LOG.warning(e.toString());
            return this.columnNames.stream().map((name) -> defaultValue).collect(Collectors.toList());
        }
    }
    
    @Override
    public final int getColumnDisplaySize(String name) {
        final int index = this.columnNames.indexOf(name);
        return this.columnDiplaySizes.get(index);
    }
    
    @Override
    public final int getColumnNullable(String name) {
        final int index = this.columnNames.indexOf(name);
        return this.columnNullables.get(index);
    }
    
    @Override
    public final int getColumnDataType(String name) {
        final int index = this.columnNames.indexOf(name);
        return this.columnDataTypes.get(index);
    }

    @Override
    public final boolean isAutoIncrement(String name) {
        final int index = this.columnNames.indexOf(name);
        return "YES".equalsIgnoreCase(this.isAutoIncrement.get(index));
    }
    
    @Override
    public final boolean isGeneratedColumn(String name) {
        final int index = this.columnNames.indexOf(name);
        return "YES".equalsIgnoreCase(this.isGeneratedColumn.get(index));
    }

    @Override
    public List<String> getColumnNames() {
        return columnNames;
    }

    @Override
    public List<Integer> getColumnNullables() {
        return columnNullables;
    }

    @Override
    public List<Integer> getColumnDataTypes() {
        return columnDataTypes;
    }

    @Override
    public List<Integer> getColumnDiplaySizes() {
        return columnDiplaySizes;
    }

    @Override
    public List<String> getIsAutoIncrement() {
        return isAutoIncrement;
    }

    @Override
    public List<String> getIsGeneratedColumn() {
        return isGeneratedColumn;
    }

    public MetaDataAccess getMetaDataAcces() {
        return metaDataAcces;
    }
}
