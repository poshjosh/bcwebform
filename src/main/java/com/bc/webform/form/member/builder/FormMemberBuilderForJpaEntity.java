package com.bc.webform.form.member.builder;

import com.bc.webform.form.member.FormInputContextForJpaEntity;
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
    protected void preBuild() {
        
        if(this.getFormInputContext() == null) {
            this.formInputContext(new FormInputContextForJpaEntity());
        }
        
        super.preBuild();
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
