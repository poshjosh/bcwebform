package com.bc.webform;

import com.bc.webform.functions.GetFormFieldTypeForAnnotatedPersistenceField;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * @author hp
 */
public class FormFieldBuilderFromAnnotatedPersistenceEntity<V> extends FormFieldBuilderImpl<V>{
    
    private static final Logger LOG = Logger.getLogger(
            FormFieldBuilderFromAnnotatedPersistenceEntity.class.getName());
    
    public FormFieldBuilderFromAnnotatedPersistenceEntity() { }

    @Override
    public Form initReferencedForm() {
        
        final Form form = this.getForm();
        final Object source = this.getSource();
        final Field field = this.getField();
        
        if(LOG.isLoggable(Level.FINER)) {
            LOG.log(Level.FINER, () -> 
                    source.getClass().getSimpleName() + '.'+field.getName() + 
                    ", form: name="+form.getName()+", parent name=" + 
                    (form.getParent()==null?null:form.getParent().getName()));            
        }

        final Form child = super.initReferencedForm();
        
        if(LOG.isLoggable(Level.FINER)) {
            if(child == null) {
                LOG.log(Level.FINER, () -> 
                        source.getClass().getSimpleName() + '.'+field.getName() + 
                        ", converted to child form: " + child);            
            }else{
                LOG.log(Level.FINER, () -> 
                        source.getClass().getSimpleName() + '.'+field.getName() + 
                        ", converted to child form: name=" + child.getName() + ", parent name=" + 
                        (child.getParent()==null?null:child.getParent().getName()));            
            }
        }

        return child;
    }
    
    public String getFormFieldName(Form form, Field field) {
        final Class fieldType = field.getType();
        final Table table = (Table)fieldType.getAnnotation(Table.class);
        final String name;
        if(table == null) {
            name = field.getName(); //fieldType.getSimpleName();
        }else{
            name = table.name();
        }    
        return name;
    }
    
//    public boolean isReferenceType(){
//        return this.getReferenceTypeOptional().isPresent();
//    }
    
    @Override
    public Map initChoices() {
        final Field field = this.getField();
        final Class fieldType = field.getType();
        if(this.getTypeTests().isEnumType(fieldType)) {
            return this.getEnumChoices(fieldType);
        }
        return super.initChoices(); 
    }
    
    public Map getEnumChoices(Class type) {
        if(this.getTypeTests().isEnumType(type)) {
            final Object [] enums = type.getEnumConstants();
            if(enums != null) {
                final Map choices = new HashMap<>(enums.length, 1.0f);
                for(int i = 0; i<enums.length; i++) {
                    choices.put((i + 1), ((Enum)enums[i]).name());
                }
                return Collections.unmodifiableMap(choices);
            }
        }
        return Collections.EMPTY_MAP;
    }

    @Override
    public boolean initMultiChoice() {
        return this.getTypeTests().isEnumType(getField().getType());
    }
    
    @Override
    public Function<Field, String> getFieldTypeFunctor(Form form, Object object, Field field) {
        return new GetFormFieldTypeForAnnotatedPersistenceField(StandardFormFieldTypes.TEXT);
    }
    
    @Override
    public int initMaxLength() {
        final Field field = this.getField();
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
        LOG.log(Level.FINER, () -> getSource().getClass().getName() + 
                '.' + field.getName() + ".maxLength = " + n);
        return maxLen;
    }
    
    @Override
    public boolean initOptional() {
        final Field field = this.getField();
        final Null nul = field.getAnnotation(Null.class);
        if(nul != null) {
            return true;
        }
        final NotNull notNull = field.getAnnotation(NotNull.class);
        if(notNull != null) {
            return false;
        }
        final Column column = field.getAnnotation(Column.class);
        if(column != null && !column.nullable()) {
            return false;
        }
        final Basic basic = field.getAnnotation(Basic.class);
        if(basic != null && !basic.optional()) {
            return false;
        }
        final Size size = field.getAnnotation(Size.class);
        if(size != null && size.min() > 0) {
            return false;
        }
        final ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
        if(manyToOne != null && manyToOne.optional()) {
            return false;
        }
        final OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        if(oneToOne != null && oneToOne.optional()) {
            return false;
        }
        return true;
    }
}
