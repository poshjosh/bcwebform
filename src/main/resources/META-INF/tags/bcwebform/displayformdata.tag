<%@tag trimDirectiveWhitespaces="true" description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://forms.looseboxes.com/jsp/jstl/bcwebform" prefix="bwf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@attribute name="form" type="com.bc.web.form.Form" required="true"%>

<div class="bwf_table">
    <c:forEach items="${form.formFields}" var="formField">
        <c:choose>
            <c:when test="${formField.value == null}"></c:when>    
            <c:otherwise>
                <c:when test="${formField.type == 'date' && 
                                formField.value.class == java.util.Date.class}">
                    <fmt:formatDate var="cfValue" pattern="${form.datePatterns[0]}" value="${formField.value}"/>   
                </c:when>    
    <%-- @see https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/datetime --%>               
    <%-- @see https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/datetime-local --%>               
                <c:when test="${formField.type == 'datetime' || formField.type == 'datetime-local' &&
                        formField.value.class == java.util.Date.class}">
                    <fmt:formatDate var="cfValue" pattern="${form.datetimePatterns[0]}" value="${formField.value}"/>   
                </c:when>    
                <c:otherwise>
                    <c:set var="cfValue" value="${formField.value}"/>        
                </c:otherwise>
            </c:otherwise>
        </c:choose>  
        <div class="bwf_row">
            <span class="bwf_column_0">${formField.label}</span>
            <span class="bwf_column_1">${cfValue}</span>
        </div>
    </c:forEach>
</div>

