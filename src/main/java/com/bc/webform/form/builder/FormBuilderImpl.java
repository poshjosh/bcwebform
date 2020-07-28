package com.bc.webform.form.builder;

import com.bc.webform.exceptions.ValuesOverwriteByDefaultException;
import com.bc.webform.form.DefaultForm;
import com.bc.webform.form.Form;
import com.bc.webform.form.FormBean;
import com.bc.webform.form.FormMemberNameMatchesParentFormName;
import com.bc.webform.form.PreferMandatory;
import com.bc.webform.form.SourceFieldsProvider;
import com.bc.webform.form.member.FormMember;
import com.bc.webform.form.member.builder.FormMemberBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author hp
 */
public class FormBuilderImpl<S, F, V> implements FormBuilder<S, F, V>{

    private static final Logger LOG = Logger.getLogger(FormBuilderImpl.class.getName());
    
    private FormBean<S> delegate;
    
    private SourceFieldsProvider<S, F> sourceFieldsProvider;
    
    private FormMemberBuilder<S, F, V> formMemberBuilder;
    
    private Predicate<FormMember<F, V>> formMemberTest;
    
    private Comparator<FormMember<F, V>> formMemberComparator;
    
    private boolean buildAttempted;

    public FormBuilderImpl() { 
        delegate = new FormBean();
    }

    @Override
    public FormBuilder<S, F, V> copy() {
        // Both delegate and dataSource are not included in copy as
        // they contain transient data
        //
        return new FormBuilderImpl()
                .formMemberBuilder(formMemberBuilder.copy())
                .formMemberComparator(formMemberComparator)
                .formMemberTest(formMemberTest)
                .sourceFieldsProvider(sourceFieldsProvider);
    }
    
    @Override
    public Form build() {
        
        this.requireBuildNotAttempted();
        
        Objects.requireNonNull(delegate);
        Objects.requireNonNull(sourceFieldsProvider);
        Objects.requireNonNull(formMemberBuilder);
        final S formDataSource = 
                Objects.requireNonNull(delegate.getDataSource());

        if(this.getFormMemberTest() == null) {
            this.formMemberTest(new FormMemberNameMatchesParentFormName().negate());
        }

        if(this.getFormMemberComparator() == null){
            this.formMemberComparator(new PreferMandatory());
        }

        delegate.checkRequiredFieldsAreSet();

        LOG.log(Level.FINE, () -> "Form data source: " + 
                (formDataSource == null ? null : formDataSource.getClass().getSimpleName()) + 
                ", form: name=" + delegate.getName() + ", parent name=" + 
                (delegate.getParent()==null?null:delegate.getParent().getName()));            

        final Set<F> sourceSet = sourceFieldsProvider.apply(delegate, formDataSource);

        final List fieldList = sourceSet.stream().map((fieldSource) -> {

            return formMemberBuilder
                    .copy() // Each builder should be used to build one and only one form member
                    .form(delegate)
                    .dataSource(fieldSource)
                    .build();

        }).filter(this.formMemberTest)
                .sorted(this.formMemberComparator)
                .collect(Collectors.toList());

        delegate.setMembers(fieldList);

        this.building(delegate);

        return delegate;
    }
    
    private void requireBuildNotAttempted() {
        if(this.buildAttempted) {
            throw new IllegalStateException("build() method may only be called once");
        }
        this.buildAttempted = true;
    }
    
    protected FormBean building(FormBean builder) {
        return builder;
    }
    
    /**
     * Apply default values.
     * 
     * The default values are gotten by building a {@link com.bc.webform.DefaultForm DefaultForm}
     * for the supplied name.
     * 
     * <p><b>Note:</b></p>
     * This method should be the first method called. Calling the method 
     * after any value has been set will lead to IllegalStateException 
     * @param name The name of the {@link com.bc.webform.DefaultForm DefaultForm} 
     * to build and whose values will be used to update the current build.
     * @throws java.lang.IllegalStateException
     * @return this object
     */
    @Override
    public FormBuilderImpl applyDefaults(String name) throws IllegalStateException{
        if(delegate.isAnyFieldSet()) {
            throw new ValuesOverwriteByDefaultException(delegate);
        }
        return this.apply(new DefaultForm(name));
    }
    
    public Form getForm() {
        return delegate;
    }

    @Override
    public FormBuilderImpl apply(Form form) {
        delegate = new FormBean(form);
        return this;
    }

    public S getFormDataSource() {
        return delegate.getDataSource();
    }
    
    @Override
    public FormBuilderImpl dataSource(S source) {
        delegate.setDataSource(source);
        return this;
    }

    public SourceFieldsProvider<S, F> getSourceFieldsProvider() {
        return sourceFieldsProvider;
    }

    @Override
    public FormBuilderImpl<S, F, V> sourceFieldsProvider(
            SourceFieldsProvider<S, F> sourceFieldsProvider) {
        this.sourceFieldsProvider = sourceFieldsProvider;
        return this;
    }

    public FormMemberBuilder getFormMemberBuilder() {
        return formMemberBuilder;
    }

    @Override
    public FormBuilderImpl<S, F, V> formMemberBuilder(FormMemberBuilder<S, F, V> formFieldBuilder) {
        this.formMemberBuilder = formFieldBuilder;
        return this;
    }

    public Predicate<FormMember<F, V>> getFormMemberTest() {
        return formMemberTest;
    }

    @Override
    public FormBuilderImpl<S, F, V> formMemberTest(Predicate<FormMember<F, V>> test) {
        this.formMemberTest = test;
        return this;
    }

    public Comparator<FormMember<F, V>> getFormMemberComparator() {
        return formMemberComparator;
    }
    
    @Override
    public FormBuilderImpl<S, F, V> formMemberComparator(Comparator<FormMember<F, V>> comparator) {
        this.formMemberComparator = comparator;
        return this;
    }
    
    public String getId() {
        return delegate.getId();
    }

    @Override
    public FormBuilderImpl<S, F, V> id(String id) {
        this.delegate.setId(id);
        return this;
    }

    public Form getParent() {
        return delegate.getParent();
    }

    @Override
    public FormBuilderImpl<S, F, V> parent(Form form) {
        this.delegate.setParent(form);
        return this;
    }

    public FormBean getDelegate() {
        return delegate;
    }
}
