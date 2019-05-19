<%@tag trimDirectiveWhitespaces="true" description="displays the options and inputs specified" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://forms.looseboxes.com/jsp/jstl/bcwebform" prefix="bwf"%>

<%@attribute name="dfsForm" required="true" type="com.bc.web.form.Form"%>
<%-- @related HTML Form fieldTypes file,text,password,select,hidden --%>
<%@attribute name="fieldTypes" required="false" description="Comma separated list of field types to display. E.g: file,text,password,hidden  If not specified all fields are displayed"%>
<%@attribute name="debug" required="false" type="java.lang.Boolean"%>
<%@attribute name="optionsMaxLength" required="false" type="java.lang.Integer" 
             description="max length for value of each option in select tag"%>

<div class="bwf_table">
   
    <c:forEach var="formField" items="${dfsForm.formFields}">

        <c:if test="${debug}">
            <p>
                <small>
                    Debug: ${debug}, Nullable: ${nullables}, Optional: ${formField.optional}<br/>
                    Type: ${formField.type}, fieldTypes: ${fieldTypes}
                </small>
            </p>
        </c:if>
        <c:if test="${(fieldTypes == null || fieldTypes == '' || fn:contains(fieldTypes, formField.type))}">
            <bwf:displayformfield debug="${debug}" dfForm="${dfsForm}" formField="${formField}"
                                  optionsMaxLength="${optionsMaxLength}"/>    
        </c:if>

    </c:forEach>
    
</div>
