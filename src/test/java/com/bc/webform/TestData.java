package com.bc.webform;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hp
 */
public class TestData {
    
    private static final AtomicInteger count = new AtomicInteger();

    public FormMemberBean newFormFieldBeanWithTestData(boolean multichoice) {
        return this.newFormFieldBeanWithTestData(this.newFormBeanWithTestData(), multichoice);
    }
    
    public FormMemberBean newFormFieldBeanWithTestData(Form form, boolean multichoice) {
        final FormMemberBean instance = this.newFormFieldBean();
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
        instance.setMultiple(false);
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
    
    public FormMemberBean newFormFieldBean() {
        return new FormMemberBean();
    }
    
    public FormBean newFormBeanWithTestData() {
        final FormBean instance = this.newFormBean();
        instance.setLabel("Sample Form");
        instance.setMembers(Arrays.asList(this.newFormFieldBeanWithTestData(instance, true)));
        instance.setId("sampleform");
        instance.setName("SampleForm");
        return instance;
    }
    
    public FormBean newFormBean() {
        return new FormBean();
    }
}
