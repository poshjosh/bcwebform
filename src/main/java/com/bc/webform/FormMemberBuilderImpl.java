package com.bc.webform;

import com.bc.webform.functions.ReferencedFormContext;
import com.bc.webform.exceptions.ValuesOverwriteByDefaultException;
import com.bc.webform.functions.IsMultiValueField;
import com.bc.webform.functions.MultiChoiceContext;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.bc.webform.functions.FormInputContext;

/**
 * @author hp
 */
public class FormMemberBuilderImpl<S, F, V> 
        implements FormMemberBuilder<S, F, V>{
    
    private static final Logger LOG = Logger.getLogger(FormMemberBuilderImpl.class.getName());
    
    private FormMemberBean<V> delegate;
    private Form form;
    private S formDataSource;
    private F dataSourceField;
    private IsMultiValueField<S, F> multiValueTest;
    private FormInputContext<S, F, V> formInputContext;
    private MultiChoiceContext<S, F> multiChoiceContext;
    private ReferencedFormContext<S, F> referencedFormContext;

    public FormMemberBuilderImpl() {
        this.delegate = new FormMemberBean<>();
    }

    @Override
    public FormMember<V> build() {

        try{

            Objects.requireNonNull(delegate);
            Objects.requireNonNull(form);
            Objects.requireNonNull(formDataSource);
            Objects.requireNonNull(dataSourceField);
            
            if(multiChoiceContext == null) {
                multiChoiceContext = MultiChoiceContext.NO_OP;
            }
            
            if(referencedFormContext == null) {
                referencedFormContext = ReferencedFormContext.NO_OP;
            }
            
            // Always return a copy to shield us from any, after the fact, 
            // changes to the original
            //
            final FormMember<V> result = buildFormField().copy();
            
            return result;
            
        }finally{
        
            this.delegate = new FormMemberBean();
        }
    }
    
    public FormMemberBean buildFormField() {
        
        final String name = this.formInputContext
                .getName(formDataSource, dataSourceField);
        
        this.applyDefaults(this.form, name);
        
        final int maxLen = this.initMaxLength();
        final int lineMaxLen = this.initLineMaxLength();
        final int numberOfLines = maxLen <= lineMaxLen ? 1 : maxLen / lineMaxLen;
        LOG.log(Level.FINER, () -> "MaxLen: " + maxLen + 
                ", lineMaxLen: " + lineMaxLen + ", numOfLines: " + numberOfLines);

        final Object value = this.formInputContext
                .getValue(formDataSource, dataSourceField);

        final boolean multiChoice = this.multiChoiceContext
                .isMultiChoice(formDataSource, dataSourceField);
        
        final Map choices = ! multiChoice ? null : 
                multiChoiceContext.getChoices(formDataSource, dataSourceField);

        final boolean mayDisplayReference = 
                value == null && 
                ! this.hasParent(this.form) && 
                this.referencedFormContext.isReferencedType(formDataSource, dataSourceField);

        final Form referencedForm = ! mayDisplayReference ? null : 
                this.referencedFormContext.createReferencedForm(
                        this.form, this.formDataSource, this.dataSourceField).orElse(null);
        
        final String referencedFormHref = ! mayDisplayReference ? null : 
                this.referencedFormContext.getReferencedFormHref(
                        this.form, this.formDataSource, this.dataSourceField).orElse(null);

        delegate.form(this.form)
                .multiValue(this.multiValueTest.isMultiValue(formDataSource, dataSourceField))
                .value(value)
                .multiChoice(multiChoice)
                .choices(choices)
                .maxLength(maxLen)
                .numberOfLines(numberOfLines)
                .optional(this.formInputContext.isOptional(formDataSource, dataSourceField))
                .referencedFormHref(referencedFormHref)
                .referencedForm(referencedForm)
                .type(this.formInputContext.getType(formDataSource, dataSourceField));

        delegate.checkRequiredFieldsAreSet();
        
        return this.building(delegate);
    }
    
    protected FormMemberBean building(FormMemberBean builder) {
        return builder;
    }
    
    /**
     * Apply default values.
     * 
     * The default values are gotten by building a {@link com.bc.webform.DefaultFormMember DefaultFormMember}
     * for the supplied name.
     * 
     * <p><b>Note:</b></p>
     * This method should be the first method called. Calling the method 
     * after any value has been set will lead to IllegalStateException 
     * @param form The form to which the form dataSourceField being built is a member.
     * @param name The name of the {@link com.bc.webform.DefaultFormMember DefaultFormMember} 
     * to build and whose values will be used to update the current build.
     * @throws com.bc.webform.exceptions.ValuesOverwriteByDefaultException
     * @return this object
     */
    @Override
    public FormMemberBuilderImpl<S, F, V> applyDefaults(Form form, String name) 
            throws com.bc.webform.exceptions.ValuesOverwriteByDefaultException{
        if(delegate.isAnyFieldSet()) {
            throw new ValuesOverwriteByDefaultException(delegate);
        }
        return this.apply(new DefaultFormMember(form, name));
    }
    
    @Override
    public FormMemberBuilderImpl<S, F, V> apply(FormMember<V> formField) {
        delegate = new FormMemberBean(formField);
        return this;
    }
    
    public Form getForm() {
        return this.form;
    }
    
    @Override
    public FormMemberBuilderImpl<S, F, V> form(Form form) {
        this.form = form;
        return this;
    }
    
    public S getFormDataSource() {
        return formDataSource;
    }

    @Override
    public FormMemberBuilderImpl<S, F, V> formDataSource(S source) {
        this.formDataSource = source;
        return this;
    }

    public F getDataSourceField() {
        return dataSourceField;
    }

    @Override
    public FormMemberBuilderImpl<S, F, V> field(F field) {
        this.dataSourceField = field;
        return this;
    }

    public IsMultiValueField<S, F> getMultiValueTest() {
        return multiValueTest;
    }

    @Override
    public FormMemberBuilderImpl<S, F, V> multiValueTest(
            IsMultiValueField<S, F> multiValueTest) {
        this.multiValueTest = multiValueTest;
        return this;
    }

    public FormInputContext<S, F, V> getFormInputContext() {
        return formInputContext;
    }

    @Override
    public FormMemberBuilderImpl<S, F, V> formInputContext(
            FormInputContext<S, F, V> fieldValueProvider) {
        this.formInputContext = fieldValueProvider;
        return this;
    }

    public MultiChoiceContext<S, F> getMultiChoiceContext() {
        return multiChoiceContext;
    }

    @Override
    public FormMemberBuilderImpl<S, F, V> multiChoiceContext(
            MultiChoiceContext<S, F> multiChoiceContext) {
        this.multiChoiceContext = multiChoiceContext;
        return this;
    }

    public ReferencedFormContext<S, F> getReferencedFormContext() {
        return referencedFormContext;
    }

    @Override
    public FormMemberBuilderImpl<S, F, V> referencedFormContext(
            ReferencedFormContext<S, F> referencedFormCreator) {
        this.referencedFormContext = referencedFormCreator;
        return this;
    }

    ///////////////////////////////////
    // non builder methods
    ///////////////////////////////////

    public boolean hasParent(Form form) {
        return form.getParent() != null;
    }

    public int initLineMaxLength() {
        return 128;
    }

    public int initMaxLength() {
        return -1;
    }
}
