package com.bc.web.form;

import com.bc.reflection.ReflectionUtil;
import com.bc.reflection.function.FindClassesInPackage;
import com.bc.web.form.domain.Blog;
import com.bc.web.form.functions.CreateFormFieldsFromAnnotatedPersistenceEntity;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Table;

/**
 * @author USER
 */
public class FormFactory {

    private static final Logger LOG = Logger.getLogger(FormFactory.class.getName());

    private static class EntityFactory implements Function<String, Object>{
        @Override
        public Object apply(String tableName) {
            final List<Class> list = new FindClassesInPackage().apply(Blog.class.getPackage().getName());
            System.out.println("Table: " + tableName);
            System.out.println(list);
            for(Class cls : list) {
                if(cls.getName().equalsIgnoreCase(tableName)) {
                    return newInstance(cls);
                }
                final Table en = (Table)cls.getAnnotation(Table.class);
                if(Objects.equals(en == null ? null : en.name(), tableName)) {
                    return newInstance(cls);
                }
            }
            throw new RuntimeException("Domain declaration not found for: " + tableName);
        }
        
        public Object newInstance(Class cls) {
            return new ReflectionUtil().newInstance(cls);
        }
    }
    
    public Form apply(String name) {
        
        try{
            
            final Object dto = new EntityFactory().apply(name);

            final Comparator<FormField> comparator = new PreferMandatoryField(); 

            return new Form.Builder()
                    .withDefault(name)
//                    .fieldsCreator(new CreateFormFieldsFromObject(){
//                    .fieldsCreator(new CreateFormFieldsFromDatabaseTable(emf){
                    .fieldsCreator(new CreateFormFieldsFromAnnotatedPersistenceEntity(){
                        @Override
                        public Form getReferencedForm(Form form, Field field) {
                            final boolean isRef = false;
                            final com.bc.web.form.Form output;
                            if(isRef) {
                                final String fieldName = field.getName();
                                final Object dto = new EntityFactory().newInstance(field.getType());
                                output = new Form.Builder()
                                        .withDefault(fieldName)
                                        .fieldsCreator(this)
                                        .fieldsComparator(comparator)
                                        .fieldDataSource(dto).build();
                            }else{
                                output = super.getReferencedForm(form, field);
                            }
                            return output;
                        }
                    })
                    .fieldsComparator(comparator)
//                    .fieldDataSource(tableName).build();
                    .fieldDataSource(dto).build();

        }catch(RuntimeException e) {
            
            LOG.log(Level.WARNING, null, e);
            
            throw e;
        }
    }
}
