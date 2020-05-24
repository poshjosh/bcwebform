package com.bc.webform;

import com.bc.webform.exceptions.ValuesOverwriteByDefaultException;
import com.bc.webform.functions.FormMemberNameMatchesParentFormName;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.bc.webform.functions.SourceFieldsProvider;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author hp
 */
public class FormBuilderImpl<S, F, V> implements FormBuilder<S, F, V>{

    private static final Logger LOG = Logger.getLogger(FormBuilderImpl.class.getName());
    
    private FormBean<S> delegate;
    
    private SourceFieldsProvider<S, F> sourceFieldsProvider;
    
    private FormMemberBuilder formMemberBuilder;
    
    private Predicate<FormMember> formFieldTest;
    
    private Comparator<FormMember> formFieldComparator;

    public FormBuilderImpl() { 
        delegate = new FormBean();
    }
    
    @Override
    public Form build() {
        
        try{
            
            Objects.requireNonNull(delegate);
            Objects.requireNonNull(sourceFieldsProvider);
            Objects.requireNonNull(formMemberBuilder);
            final S formDataSource = 
                    Objects.requireNonNull(delegate.getDataSource());
            
            if(formFieldTest == null) {
                formFieldTest = new FormMemberNameMatchesParentFormName().negate();
            }
            
            if(formFieldComparator == null){
                formFieldComparator = new PreferMandatory();
            }
            
            delegate.checkRequiredFieldsAreSet();
            
            LOG.log(Level.FINE, () -> "Form data source: " + 
                    (formDataSource == null ? null : formDataSource.getClass().getSimpleName()) + 
                    ", form: name=" + delegate.getName() + ", parent name=" + 
                    (delegate.getParent()==null?null:delegate.getParent().getName()));            

            final Set<F> sourceSet = sourceFieldsProvider.apply(delegate, formDataSource);

            final List fieldList = sourceSet.stream().map((fieldSource) -> {

                return formMemberBuilder
                        .form(delegate)
                        .dataSource(fieldSource)
                        .build();

            }).filter(this.formFieldTest)
                    .sorted(this.formFieldComparator)
                    .collect(Collectors.toList());

            delegate.setFormFields(fieldList);

            this.building(delegate);
            
            // Always return a copy to shield us from any, after the fact, 
            // changes to the original
            //
            final Form result = delegate.copy();

            return result;

        }finally{
            
            this.delegate = new FormBean();
        }
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
    public FormBuilderImpl<S, F, V> formMemberBuilder(FormMemberBuilder formFieldBuilder) {
        this.formMemberBuilder = formFieldBuilder;
        return this;
    }

    public Predicate<FormMember> getFormFieldTest() {
        return formFieldTest;
    }

    @Override
    public FormBuilderImpl<S, F, V> formMemberTest(Predicate<FormMember> test) {
        this.formFieldTest = test;
        return this;
    }

    public Comparator<FormMember> getFormFieldComparator() {
        return formFieldComparator;
    }
    
    @Override
    public FormBuilderImpl<S, F, V> formMemberComparator(Comparator<FormMember> comparator) {
        this.formFieldComparator = comparator;
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
