<%@ include file="../common/header.jsp" %>

<c:choose>
    <c:when test="${mode == 'edit'}">
        <h2>Edit Author</h2>
        <c:set var="action" value="/authors/${author.id}"/>
    </c:when>
    <c:otherwise>
        <h2>Add Author</h2>
        <c:set var="action" value="/authors"/>
    </c:otherwise>
</c:choose>

<form:form modelAttribute="author" action="${pageContext.request.contextPath}${action}" method="post">
    <div class="field">
        <label for="name">Name</label>
        <form:input path="name" id="name"/>
        <form:errors path="name" cssClass="error" element="div"/>
    </div>

    <div class="field">
        <label for="nationality">Nationality</label>
        <form:input path="nationality" id="nationality"/>
        <form:errors path="nationality" cssClass="error" element="div"/>
    </div>

    <div class="field">
        <label for="birthYear">Birth Year</label>
        <form:input path="birthYear" id="birthYear" type="number"/>
        <form:errors path="birthYear" cssClass="error" element="div"/>
    </div>

    <button type="submit" class="btn">
        <c:choose><c:when test="${mode == 'edit'}">Update Author</c:when><c:otherwise>Create Author</c:otherwise></c:choose>
    </button>
    <a class="btn btn-light" href="<c:url value='/authors'/>">Cancel</a>
</form:form>

<%@ include file="../common/footer.jsp" %>
