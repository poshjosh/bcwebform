package com.bc.webform.form.member.builder;

import com.bc.webform.WebformUtil;
import com.bc.webform.choices.SelectOption;
import com.bc.webform.choices.SelectOptionBean;
import com.bc.webform.form.Form;
import com.bc.webform.exceptions.ValuesOverwriteByDefaultException;
import com.bc.webform.form.FormBean;
import com.bc.webform.form.member.DefaultFormMember;
import com.bc.webform.form.member.FormInputContext;
import com.bc.webform.form.member.FormMember;
import com.bc.webform.form.member.FormMemberBean;
import com.bc.webform.form.member.MultiChoiceContext;
import com.bc.webform.form.member.ReferencedFormContext;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.bc.webform.functions.IsMultipleInput;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Build and instance of {@link com.bc.webform.form.member.FormMember} for
 * a {@link com.bc.webform.form.Form}.
 * 
 * <b>Note</b> If you return an instance of {@link com.bc.webform.choices.SelectOption}
 * from {@link com.bc.webform.form.member.FormInputContext#getValue(java.lang.Object, java.lang.Object) FormInputContext#getValue},
 * then the returned value is used to populated a single item list of choices
 * for the related {@link com.bc.webform.form.member.FormMember}.
 * 
 * @author hp
 */
public class FormMemberBuilderImpl<S, F, V> 
        implements FormMemberBuilder<S, F, V>{
    
    private static final Logger LOG = Logger.getLogger(FormMemberBuilderImpl.class.getName());
    
    private FormMemberBean<F, V> delegate;
    private FormBean<S> form;
    private F dataSource;
    private IsMultipleInput<S, F> multipleInputTest;
    private FormInputContext<S, F, V> formInputContext;
    private MultiChoiceContext<S, F> multiChoiceContext;
    private ReferencedFormContext<S, F> referencedFormContext;
    
    private boolean buildAttempted;

    public FormMemberBuilderImpl() {
        this.delegate = new FormMemberBean<>();
    }

    public List<FormMember<F, V>> build(Set<F> sourceSet) {
        
        final List<FormMember<F, V>> fieldList = sourceSet.stream().map((fieldSource) -> {

            return this.form(this.form)
                    .dataSource(fieldSource)
                    .build();

        }).collect(Collectors.toList());
        
        return fieldList;
    }
    
    protected void initDefaults() {
        if(multiChoiceContext == null) {
            multiChoiceContext = MultiChoiceContext.NO_OP;
        }
        if(referencedFormContext == null) {
            referencedFormContext = ReferencedFormContext.NO_OP;
        }
    }
    
    protected void preBuild() {}
    
    protected void validate() {
        Objects.requireNonNull(delegate);
        Objects.requireNonNull(form);
        Objects.requireNonNull(form.getDataSource());
        Objects.requireNonNull(dataSource);
    }

    @Override
    public final FormMember<F, V> build() {

        this.requireBuildNotAttempted();
        
        this.preBuild();
        
        this.initDefaults();
        
        this.validate();

        return buildFormField();
    }
    
    private void requireBuildNotAttempted() {
        if(this.buildAttempted) {
            throw new IllegalStateException("build() method may only be called once");
        }
        this.buildAttempted = true;
    }
    
    public FormMemberBean<F, V> buildFormField() {
        
        final S formDataSource = Objects.requireNonNull(form.getDataSource());
        
        final String name = this.formInputContext
                .getName(formDataSource, dataSource);
        
        this.applyDefaults(this.form, name);
        
        final int maxLen = this.initMaxLength();
        final int lineMaxLen = this.initLineMaxLength();
        final int numberOfLines = maxLen <= lineMaxLen ? 1 : maxLen / lineMaxLen;
        LOG.log(Level.FINEST, () -> "MaxLen: " + maxLen + 
                ", lineMaxLen: " + lineMaxLen + ", numOfLines: " + numberOfLines);

        final V value = formInputContext.getValue(formDataSource, dataSource);
        
        final boolean multiChoice = multiChoiceContext.isMultiChoice(formDataSource, dataSource);
        
        final List<SelectOption> choices = ! multiChoice ? null : 
                multiChoiceContext.getChoices(formDataSource, dataSource, value);

        final boolean isReferenceType = 
//                value == null && 
//                ! this.hasParent(this.form) && 
                this.referencedFormContext.isReferencedType(form, dataSource);

        delegate.form(this.form)
                .dataSource(this.dataSource)
                .multiple(this.multipleInputTest.isMultiple(formDataSource, dataSource))
                .value(value)
                .multiChoice(multiChoice)
                .choices(choices == null ? null : (List<SelectOptionBean>)choices.stream().map(WebformUtil::toBean).collect(Collectors.toList()))
                .maxLength(maxLen)
                .numberOfLines(numberOfLines)
                .optional(this.formInputContext.isOptional(formDataSource, dataSource))
                .formReference(isReferenceType)
                .type(this.formInputContext.getType(formDataSource, dataSource))
                .dataType(formInputContext.getDataType(formDataSource, dataSource));

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
        delegate = WebformUtil.toBean(formField);
        return this;
    }
    
    public Form<S> getForm() {
        return this.form;
    }
    
    @Override
    public FormMemberBuilderImpl<S, F, V> form(Form form) {
        this.form = WebformUtil.toBean(form);
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

    public IsMultipleInput<S, F> getMultipleInputTest() {
        return multipleInputTest;
    }

    @Override
    public FormMemberBuilderImpl<S, F, V> multipleInputTest(
            IsMultipleInput<S, F> multipleInputTest) {
        this.multipleInputTest = multipleInputTest;
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
