# bcwebform

### Use bcwebform to create dynamic web forms

### Create the form bean

```java
        final Person entity = new Person();

        final DefaultForm defaultForm = new DefaultForm(tableName);

        final Comparator<FormField> comparator = new PreferMandatoryField(); 

        final Form form = new Form.Builder()
                .apply(defaultForm)
                .fieldsCreator(new CreateFormFieldFromAnnotatedPersistenceEntity())
                .fieldsComparator(comparator)
                .fieldDataSource(entity).build();
```

### The form is a bean to be used in a web page   

`form.formFields` refer to a collection for form fields each of which could be 
used as below (in a jsp tag).

```xml
            <div class="bwf_row">
                <span class="bwf_column_0">
                  ${formField.label}<c:if test="${formField.required}"><font class="bwf_heavy_red">*</font></c:if>
                </span>
                <div class="bwf_column_1">

                    <c:if test="${debug != null && debug}">
                        <small>    
                            Form field: ${formField.name}&emsp;=&emsp;${formField.value},&emsp;Choices:<br/>
                            <c:forEach var="pair" items="${formField.choices}">
                                ${pair.key}&emsp;=&emsp;${pair.value}
                                <br/>
                            </c:forEach>    
                        </small>
                    </c:if>
                    <c:choose>
                        <c:when test="${formField.multiValue}">
                            <c:forEach varStatus="vs" var="pair" items="${formField.choices}">
                                <c:choose>
                                    <c:when test="${formField.value != null && 
                                                    (fn:contains(formField.value, pair.key) || fn:contains(formField.value, pair.value))}">
                                        <input checked="checked" 
                                               type="${formField.type}" class="bwf_input" 
                                               id="${formField.id}${vs.index}" name="${formField.name}"
                                               value="${pair.key}"
                                               required="${formField.required}" />${pair.value}
                                    </c:when>
                                    <c:otherwise>
                                        <input type="${formField.type}" class="bwf_input" 
                                               id="${formField.id}${vs.index}" name="${formField.name}"
                                               value="${pair.key}"
                                               required="${formField.required}" />${pair.value}
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:when>
                        <c:when test="${formField.multiChoice}">
                            <select style="width:${formField.size/1.67}" class="bwf_select" 
                                    id="${formField.id}" name="${formField.name}" size="1"
                                    required="${formField.required}">

                                <option disabled>
                                    Select <bwf:truncate ellipsis=".." maxLength="${optionsMaxLength}" target="${formField.label}"/>  
                                </option>

                                <c:forEach var="pair" items="${formField.choices}">
                                    <c:choose>
                                        <c:when test="${pair.key.toString().equalsIgnoreCase(formField.value) || pair.value.toString().equalsIgnoreCase(formField.value)}">
                                            <option value="${pair.key}" selected>
                                                <bwf:truncate ellipsis=".." maxLength="${optionsMaxLength}" target="${pair.value}"/>  
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${pair.key}">
                                                <bwf:truncate ellipsis=".." maxLength="${optionsMaxLength}" target="${pair.value}"/>  
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </c:when>
                        <c:otherwise>

                            <c:choose>
                                <c:when test="${formField.type == 'password'}">

                                    <input type="${formField.type}" class="bwf_input" id="${formField.id}" name="${formField.name}"
                                           size="${formField.size}" maxlength="${formField.maxLength}" value="${formField.value}"
                                           required="${formField.required}"/>
                                </c:when>
                                <c:when test="${formField.type == 'checkbox'}">

                                    <input type="${formField.type}" class="bwf_input" 
                                           id="${formField.id}" name="${formField.name}"
                                           required="${formField.required}"/>

                                </c:when>
                                <c:when test="${formField.numberOfLines < 2}">

                                    <input type="${formField.type}" class="bwf_input" id="${formField.id}" name="${formField.name}"
                                           size="${formField.size}" maxlength="${formField.maxLength}" value="${formField.value}"
                                           required="${formField.required}"/>

                                </c:when>
                                <c:otherwise>

                                    <textarea rows="${formField.numberOfLines}" class="bwf_textarea" 
                                              id="${formField.id}" name="${formField.name}"
                                              required="${formField.required}">
                                        ${formField.value}
                                    </textarea>
                                </c:otherwise>
                            </c:choose>

                        </c:otherwise>
                    </c:choose>    

                </div>
                <span class="bwf_column_2">
                    <span id="${formField.name}Message">
                        &nbsp;&nbsp;&nbsp;<small>${formField.advice}</small>
                    </span>
                </span>
            </div>
```
