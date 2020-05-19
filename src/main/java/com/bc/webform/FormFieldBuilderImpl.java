package com.bc.webform;

import com.bc.reflection.ReflectionUtil;
import com.bc.webform.exceptions.ValuesOverwriteByDefaultException;
import com.bc.webform.functions.GetFormFieldTypeForField;
import com.bc.webform.functions.IsPasswordField;
import com.bc.webform.functions.TypeTests;
import com.bc.webform.functions.TypeTestsImpl;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hp
 */
public class FormFieldBuilderImpl<V> 
        implements FormFieldBuilderFromSource<Object, Field, V>{
    
    private static final Logger LOG = Logger.getLogger(FormFieldBuilderImpl.class.getName());
    
    private FormFieldBean delegate;
    private Form form;
    private Object source;
    private Field field;
    private TypeTests typeTests;
    private ReferenceFormCreator<Object, Field> referenceFormCreator;

    public FormFieldBuilderImpl() {
        this.delegate = new FormFieldBean();
    }
    
    @Override
    public FormFieldBuilderImpl reset() {
        this.delegate = new FormFieldBean();
        this.form = null;
        this.source = null;
        this.field = null;
        this.typeTests = null;
        this.referenceFormCreator = null;
        return this;
    }

    @Override
    public FormField<V> build() {

        try{
            
            Objects.requireNonNull(form);
            Objects.requireNonNull(source);
            Objects.requireNonNull(field);
            
            if(typeTests == null) {
                typeTests = new TypeTestsImpl();
            }
            
            if(referenceFormCreator == null) {
                referenceFormCreator = ReferenceFormCreator.NO_OP;
            }
            
            final int maxLen = this.initMaxLength();
            final int lineMaxLen = this.initLineMaxLength();
            final int numberOfLines = maxLen <= lineMaxLen ? 1 : maxLen / lineMaxLen;
            LOG.log(Level.FINER, () -> "MaxLen: " + maxLen + 
                    ", lineMaxLen: " + lineMaxLen + ", numOfLines: " + numberOfLines);
            
            final Object value = this.initValue();

            final boolean multiChoice = this.initMultiChoice();
            
            final boolean mayDisplayReference = 
                    value == null && 
                    ! this.hasParent(this.form) && 
                    this.initReferenceType();
            
            final Class fieldType = field.getType();
            
            this.applyDefaults(this.form, field.getName())
                    .form(this.form);
            
            delegate.form(this.form)
                    .multiValue(this.typeTests.isContainerType(fieldType))
                    .value(value)
                    .multiChoice(multiChoice)
                    .choices(multiChoice ? this.initChoices() : null)
                    .maxLength(maxLen)
                    .numberOfLines(numberOfLines)
                    .optional(this.initOptional())
                    .referencedFormHref(mayDisplayReference ? this.initReferencedFormHref() : null)
                    .referencedForm(mayDisplayReference ? this.initReferencedForm() : null)
                    .type(this.initType());
            
            delegate.checkRequiredFieldsAreSet();
            
            this.building(delegate);

            // Always return a copy to shield us from any, after the fact, 
            // changes to the original
            //
            final FormField<V> result = delegate.copy();
            
            return result;
            
        }finally{
        
            this.reset();
        }
    }
    
    protected FormFieldBean building(FormFieldBean builder) {
        return builder;
    }
    
    /**
     * Apply default values.
     * 
     * The default values are gotten by building a {@link com.bc.webform.DefaultFormField DefaultFormField}
     * for the supplied name.
     * 
     * <p><b>Note:</b></p>
     * This method should be the first method called. Calling the method 
     * after any value has been set will lead to IllegalStateException 
     * @param form The form to which the form field being built is a member.
     * @param name The name of the {@link com.bc.webform.DefaultFormField DefaultFormField} 
     * to build and whose values will be used to update the current build.
     * @throws java.lang.IllegalStateException
     * @return this object
     */
    public FormFieldBuilderImpl applyDefaults(Form form, String name) {
        if(delegate.isAnyFieldSet()) {
            throw new ValuesOverwriteByDefaultException(delegate);
        }
        return this.apply(new DefaultFormField(form, name));
    }
    
    @Override
    public FormFieldBuilderImpl apply(FormField<V> formField) {
        delegate = new FormFieldBean(formField);
        return this;
    }
    
    //////////////////////////////
    // builder methods
    //////////////////////////////
    
    public Form getForm() {
        return this.form;
    }
    
    @Override
    public FormFieldBuilderImpl form(Form form) {
        this.form = form;
        return this;
    }
    
    public Object getSource() {
        return source;
    }

    @Override
    public FormFieldBuilderImpl source(Object source) {
        this.source = source;
        return this;
    }

    public Field getField() {
        return field;
    }

    @Override
    public FormFieldBuilderImpl field(Field field) {
        this.field = field;
        return this;
    }

    public FormFieldBuilderImpl typeTests(TypeTests typeTests) {
        this.typeTests = typeTests;
        return this;
    }

    public TypeTests getTypeTests() {
        return typeTests;
    }

    public FormFieldBuilderImpl referenceFormCreator(
            ReferenceFormCreator<Object, Field> referenceFormCreator) {
        this.referenceFormCreator = referenceFormCreator;
        return this;
    }

    public ReferenceFormCreator<Object, Field> getReferenceFormCreator() {
        return referenceFormCreator;
    }
//////////////////////////////
    
    public boolean hasParent(Form form) {
        return form.getParent() != null;
    }

    public boolean initReferenceType() {
        final boolean output;
        final Class fieldType = field.getType();
        if(typeTests.isEnumType(fieldType)) {
            output = false;
        }else if(typeTests.isDomainType(fieldType)){
            output = true;
        }else{
            output = false;
        }
        LOG.log(Level.FINER, "Field: {0}, is reference type: {1}",
                new Object[]{field.getName(), output});
        return output;
    }
    
    public String initReferencedFormHref() {
        return null;
    }
    
    public Form initReferencedForm() {
        return this.referenceFormCreator.createReferenceForm(
                this.getForm(), source, field).orElse(null);
    }

    public boolean initMultiChoice() {
        return false;
    }

    public Map initChoices() {
        return null;
    }

    public Object initValue() {
        Object value = this.getValueFromField();
        if(value == null) {
            value = this.getValueFromMethod();
        }
        if(LOG.isLoggable(Level.FINER)) {
            LOG.log(Level.FINER, "{0}.{1} = {2}", 
                    new Object[]{
                        (source==null?null:source.getClass().getSimpleName()),
                        field.getName(),
                        value});
        }
        return value;
    }

    public Object getValueFromField() {
        Object fieldValue = null;
        if(source != null) {
            final boolean flag = field.isAccessible();
            try{
                if( ! flag) {
                    field.setAccessible(true);
                }
                fieldValue = field.get(source);
                if(LOG.isLoggable(Level.FINER)) {
                    LOG.log(Level.FINER, "Retreived value: {0}, from field: {1}", new Object[]{fieldValue, field});
                }
            }catch(IllegalArgumentException | IllegalAccessException e) {
                LOG.log(Level.WARNING, "Failed to access value for field: " + field, e);
            }finally{
                if( ! flag) {
                    field.setAccessible(flag);
                }
            }
        }
        return fieldValue;
    }
    
    public Object getValueFromMethod() {
        Object methodValue = null;
        if(source != null) {
            final Class objectType = source.getClass();
            final Method [] methods = this.getMethods(objectType);
            if(methods != null && methods.length > 0) {
                try{
                    methodValue = new ReflectionUtil()
                            .getValue(objectType, source, methods, field.getName());
                }catch(RuntimeException ignored) { }
            }
            if(LOG.isLoggable(Level.FINER)) {
                LOG.log(Level.FINER, "Retreived from associated method, value: {0}, field: {1}", new Object[]{methodValue, field});
            }
        }
        return methodValue;
    }
    
    private transient Method [] _methods;
    public Method [] getMethods(Class type) {
        if(this._methods == null || this._methods.length == 0) {
            this._methods = type.getDeclaredMethods();
        }else{
            if( ! this._methods[0].getDeclaringClass().equals(type)) {
                this._methods = type.getDeclaredMethods();
            }
        }
        return _methods;
    }

    public int initLineMaxLength() {
        return 128;
    }

    public int initMaxLength() {
        return -1;
    }
    
    public boolean initOptional() {
        return false;
    }

    public String initType() {
        final String type;
        if(new IsPasswordField().test(field.getName())) {
            type = StandardFormFieldTypes.PASSWORD; 
        }else{
            type = this.getFieldTypeFunctor(getForm(), getSource(), field).apply(field);
        }
        return type;
    }    

    public Function<Field, String> getFieldTypeFunctor(Form form, Object object, Field field) {
        return new GetFormFieldTypeForField(StandardFormFieldTypes.TEXT);
    }

    public FormFieldBean getDelegate() {
        return delegate;
    }
}
