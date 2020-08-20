package com.bc.webform.form.member.builder;

import com.bc.webform.TypeTestsImpl;
import com.bc.webform.form.member.FormInputContext;
import com.bc.webform.form.member.FormInputContextForJpaEntity;
import com.bc.webform.form.member.MultiChoiceContext;
import com.bc.webform.form.member.MultiChoiceContextForPojo;
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

    public FormMemberBuilderForJpaEntity() { 
        this(new FormInputContextForJpaEntity(), new MultiChoiceContextForPojo(new TypeTestsImpl()));
    }
    
    public FormMemberBuilderForJpaEntity(
            FormInputContext<Object, Field, Object> formInputContext,
            MultiChoiceContext<Object, Field> multiChoiceContext) { 
        super(formInputContext, multiChoiceContext);
    }

    @Override
    public int initMaxLength() {
        final Field field = this.getDataSource();
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
        
        LOG.log(Level.FINEST, () -> getForm().getDataSource().getClass().getName() + 
                '.' + field.getName() + ".maxLength = " + n);
        return maxLen;
    }
}
