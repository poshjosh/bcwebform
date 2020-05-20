package com.bc.webform;

import com.bc.webform.functions.FormInputContext;
import com.bc.webform.functions.FormInputContextForJpaEntity;
import com.bc.webform.functions.MultiChoiceContextForJpaEntity;
import com.bc.webform.functions.TypeTests;
import com.bc.webform.functions.TypeTestsImpl;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.validation.constraints.Size;

/**
 * @author hp
 */
public class FormMemberBuilderForJpaEntity extends FormMemberBuilderForPojo{
    
    private static final Logger LOG = Logger.getLogger(FormMemberBuilderForJpaEntity.class.getName());
    
    public FormMemberBuilderForJpaEntity() { }

    @Override
    public FormMember<Object> build() {
        
        if(this.getFormInputContext() == null) {
            this.formInputContext(new FormInputContextForJpaEntity());
        }
        
        if(this.getMultiChoiceContext() == null) {
            this.multiChoiceContext(new TypeTestsImpl());
        }
        
        return super.build();
    }

    public FormMemberBuilderForJpaEntity multiChoiceContext(TypeTests typeTests) {
        this.multiChoiceContext(new MultiChoiceContextForJpaEntity(typeTests));
        return this;
    }

    @Override
    public FormMemberBuilderImpl<Object, Field, Object> formInputContext(
            FormInputContext<Object, Field, Object> fieldValueProvider) {
        return super.formInputContext(fieldValueProvider);
    }
    
    @Override
    public int initMaxLength() {
        final Field field = this.getDataSourceField();
        final Size size = field.getAnnotation(Size.class);
        int maxLen = -1;
        if(size != null) {
            maxLen = size.max();
        }
        if(maxLen == -1) {
            final Column column = field.getAnnotation(Column.class);
            maxLen = column == null ? -1 : column.length();
        }
        final int n = maxLen;
        LOG.log(Level.FINER, () -> getFormDataSource().getClass().getName() + 
                '.' + field.getName() + ".maxLength = " + n);
        return maxLen;
    }
}
