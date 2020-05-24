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
    
    private FormMemberBean<F, V> delegate;
    private Form<S> form;
    private F dataSource;
    private IsMultiValueField<S, F> multiValueTest;
    private FormInputContext<S, F, V> formInputContext;
    private MultiChoiceContext<S, F> multiChoiceContext;
    private ReferencedFormContext<S, F> referencedFormContext;

    public FormMemberBuilderImpl() {
        this.delegate = new FormMemberBean<>();
    }

    @Override
    public FormMember<F, V> build() {

        try{

            Objects.requireNonNull(delegate);
            Objects.requireNonNull(form);
            Objects.requireNonNull(form.getDataSource());
            Objects.requireNonNull(dataSource);
            
            if(multiChoiceContext == null) {
                multiChoiceContext = MultiChoiceContext.NO_OP;
            }
            
            if(referencedFormContext == null) {
                referencedFormContext = ReferencedFormContext.NO_OP;
            }
            
            // Always return a copy to shield us from any, after the fact, 
            // changes to the original
            //
            final FormMember<F, V> result = buildFormField().copy();
            
            return result;
            
        }finally{
        
            this.delegate = new FormMemberBean();
        }
    }
    
    public FormMemberBean<F, V> buildFormField() {
        
        final S formDataSource = Objects.requireNonNull(form.getDataSource());
        
        final String name = this.formInputContext
                .getName(formDataSource, dataSource);
        
        this.applyDefaults(this.form, name);
        
        final int maxLen = this.initMaxLength();
        final int lineMaxLen = this.initLineMaxLength();
        final int numberOfLines = maxLen <= lineMaxLen ? 1 : maxLen / lineMaxLen;
        LOG.log(Level.FINER, () -> "MaxLen: " + maxLen + 
                ", lineMaxLen: " + lineMaxLen + ", numOfLines: " + numberOfLines);

        final V value = this.formInputContext
                .getValue(formDataSource, dataSource);
        
        final boolean multiChoice = this.multiChoiceContext
                .isMultiChoice(formDataSource, dataSource);
        
        final Map choices = ! multiChoice ? null : 
                multiChoiceContext.getChoices(formDataSource, dataSource);

        final boolean mayDisplayReference = 
                value == null && 
                ! this.hasParent(this.form) && 
                this.referencedFormContext.isReferencedType(formDataSource, dataSource);

        final Form referencedForm = ! mayDisplayReference ? null : 
                this.referencedFormContext.createReferencedForm(
                        this.form, formDataSource, dataSource).orElse(null);
        
        final String referencedFormHref = ! mayDisplayReference ? null : 
                this.referencedFormContext.getReferencedFormHref(
                        this.form, formDataSource, dataSource).orElse(null);

        delegate.form(this.form)
                .dataSource(this.dataSource)
                .multiValue(this.multiValueTest.isMultiValue(formDataSource, dataSource))
                .value(value)
                .multiChoice(multiChoice)
                .choices(choices)
                .maxLength(maxLen)
                .numberOfLines(numberOfLines)
                .optional(this.formInputContext.isOptional(formDataSource, dataSource))
                .referencedFormHref(referencedFormHref)
                .referencedForm(referencedForm)
                .type(this.formInputContext.getType(formDataSource, dataSource));

        delegate.checkRequiredFieldsAreSet();
        
        return this.building(delegate);
    }
    
    protected FormMemberBean<F, V> building(FormMemberBean<F, V> builder) {
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
    public FormMemberBuilderImpl<S, F, V> apply(FormMember<F, V> formField) {
        delegate = new FormMemberBean(formField);
        return this;
    }
    
    public Form<S> getForm() {
        return this.form;
    }
    
    @Override
    public FormMemberBuilderImpl<S, F, V> form(Form form) {
        this.form = form;
        return this;
    }

    public F getDataSource() {
        return this.dataSource;
    }

    @Override
    public FormMemberBuilderImpl<S, F, V> dataSource(F dataSource) {
        this.dataSource = dataSource;
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

    public FormMemberBean<F, V> getDelegate() {
        return delegate;
    }
}