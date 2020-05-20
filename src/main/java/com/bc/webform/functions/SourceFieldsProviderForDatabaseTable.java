package com.bc.webform.functions;

import com.bc.db.meta.access.MetaDataAccess;
import com.bc.db.meta.access.MetaDataAccessImpl;
import com.bc.webform.Form;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;

/**
 * @author hp
 */
public class SourceFieldsProviderForDatabaseTable 
        implements SourceFieldsProvider<String, String>{
    
    private final MetaDataAccess metaDataAccess;
    private final BiPredicate<String, String> isFormField;

    public SourceFieldsProviderForDatabaseTable(EntityManagerFactory emf) {
        this(new MetaDataAccessImpl(emf), new IsFormFieldTestForDatabaseTable(emf));
    }
        
    public SourceFieldsProviderForDatabaseTable(
            EntityManagerFactory emf, BiPredicate<String, String> isFormField) {
        this(new MetaDataAccessImpl(emf), isFormField);
    }
    
    public SourceFieldsProviderForDatabaseTable(
            MetaDataAccess metaDataAccess, BiPredicate<String, String> isFormField) {
        this.metaDataAccess = Objects.requireNonNull(metaDataAccess);
        this.isFormField = Objects.requireNonNull(isFormField);
    }

    @Override
    public Set<String> apply(Form form, String table) {
        return metaDataAccess.fetchStringMetaData(table, MetaDataAccess.COLUMN_NAME)
                .stream()
                .filter((col) -> this.isFormField.test(table, col))
                .collect(Collectors.toSet());
    }
}
