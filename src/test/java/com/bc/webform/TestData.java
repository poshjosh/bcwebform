package com.bc.webform;

import com.bc.webform.FormFieldBean;
import com.bc.webform.FormBean;
import com.bc.webform.Form;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hp
 */
public class TestData {
    
    private static final AtomicInteger count = new AtomicInteger();

    public FormFieldBean newFormFieldBeanWithTestData(boolean multichoice) {
        return this.newFormFieldBeanWithTestData(this.newFormBeanWithTestData(), multichoice);
    }
    
    public FormFieldBean newFormFieldBeanWithTestData(Form form, boolean multichoice) {
        final FormFieldBean instance = this.newFormFieldBean();
        instance.setAdvice("Sample advice");
        if(multichoice) {
            instance.setChoices(Collections.singletonMap("1", "Choice 1"));
        }else{
            instance.setChoices(Collections.EMPTY_MAP);
        }
        instance.setForm(form);
        instance.setId("formfield_"+(count.incrementAndGet()));
        instance.setLabel("Sample Form Field - " + (count.incrementAndGet()));
        instance.setMaxLength(512);
        instance.setMultiChoice(multichoice);
        instance.setMultiValue(false);
        instance.setName("SampleFormField_"+(count.incrementAndGet()));
        instance.setNumberOfLines(2);
        instance.setOptional(false);
        instance.setReferencedForm(null);
        instance.setRequired(!instance.isOptional());
        instance.setSize(35);
        instance.setType("sampleType");
        instance.setValue("Form Field " + count.incrementAndGet() + " Field Value");
        return instance;
    }
    
    public FormFieldBean newFormFieldBean() {
        return new FormFieldBean();
    }
    
    public FormBean newFormBeanWithTestData() {
        final FormBean instance = this.newFormBean();
        instance.setDatePatterns(Arrays.asList("MM-dd-yyyy"));
        instance.setDatetimePatterns(Arrays.asList("yyyy-MM-ddTHH:mm:ss"));
        instance.setLabel("Sample Form");
        instance.setFormFields(Arrays.asList(this.newFormFieldBeanWithTestData(instance, true)));
        instance.setId("sampleform");
        instance.setName("SampleForm");
        instance.setTimePatterns(Arrays.asList("HH:mm:ss"));
        return instance;
    }
    
    public FormBean newFormBean() {
        return new FormBean();
    }
}
