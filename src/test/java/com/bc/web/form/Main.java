package com.bc.web.form;

import com.bc.web.form.functions.CreateFormFieldFromAnnotatedPersistenceEntity;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author USER
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private static class GetEntityFormForEntitySimpleName implements Function<String, Object>{
        @Override
        public Object apply(String tableName) {
            final Object entityForm;
            try{
                final Class srcType = Class.forName(Testdocument.class.getPackage().getName() + '.' + tableName);
//                final Class tgtType = FieldsDataTransfer.of(new GetFormTypeForEntityType()).newTargetType(srcType);
                final Class tgtType = srcType;
                entityForm = tgtType.getConstructor().newInstance();
            }catch(Exception e) {
                throw new RuntimeException(e);
            }
            return entityForm;
        }
    }
    
    public void a() {
        
        try{
            
            final String tableName = "";

            final Object entityForm = new GetEntityFormForEntitySimpleName().apply(tableName);

            final List<String> multichoice = Arrays.asList("usergroup", "userstatus", "gender", 
                    "messagetype", "mimetype", "messagestatus", "permission", "role");
            
            final DefaultForm defaultForm = new DefaultForm(tableName);
            final Comparator<FormField> comparator = new PreferMandatoryField(); 
//          new CreateFormFieldFromAnnotatedPersistenceEntity(gender)
//          new CreateFormFieldsFromObject(gender)
//          new CreateFormFieldsFromDatabaseTable(emf)
            final Form form = new Form.Builder()
                    .apply(defaultForm)
//                    .fieldsCreator(new CreateFormFieldsFromDatabaseTable(emf){
                    .fieldsCreator(new CreateFormFieldFromAnnotatedPersistenceEntity(){
                        @Override
                        public Form getReferencedForm(Form form, Field field) {
                            final boolean isRef = false;
                            final com.bc.web.form.Form output;
                            if(isRef) {
                                final String fieldName = field.getName();
                                final String tableName = Character.toTitleCase(fieldName.charAt(0)) + fieldName.substring(1);
                                final Object entityForm = new GetEntityFormForEntitySimpleName().apply(tableName);
                                output = new Form.Builder()
                                        .apply(defaultForm)
                                        .fieldsCreator(this)
                                        .fieldsComparator(comparator)
                                        .fieldDataSource(entityForm).build();
                            }else{
                                output = super.getReferencedForm(form, field);
                            }
                            return output;
                        }
                    })
                    .fieldsComparator(comparator)
//                    .fieldDataSource(tableName).build();
                    .fieldDataSource(entityForm).build();

        }catch(RuntimeException e) {
            
            LOG.log(Level.WARNING, null, e);
            
            throw e;
        }
    }
}
