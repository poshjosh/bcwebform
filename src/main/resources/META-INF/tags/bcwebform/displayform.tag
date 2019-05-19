<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://forms.looseboxes.com/jsp/jstl/bcwebform" prefix="bwf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@attribute name="form" required="true" type="com.bc.web.form.Form"%>
<%@attribute name="debug" required="false" type="java.lang.Boolean"%>
<%@attribute name="optionsMaxLength" required="false" type="java.lang.Integer" 
             description="max length for value of each option in select tag"%>

<p class="bwf_bold">Asterixed (<font class="bwf_heavy_red">*</font>) fields are mandatory.</p>
<bwf:displayformfields debug="${debug}" dfsForm="${form}"
                       optionsMaxLength="${optionsMaxLength}"/>
    
<c:forEach var="formField" items="${form.formFields}">
  <c:if test="${formField.formReference}">
    <p class="bwf_bold">Add ${formField.label}</p>
    <bwf:displayformfields debug="${debug}" dfsForm="${formField.referencedForm}"
                           optionsMaxLength="${optionsMaxLength}"/>
  </c:if>
</c:forEach>  
