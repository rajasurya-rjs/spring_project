<%@ include file="../common/header.jsp" %>

<c:choose>
    <c:when test="${mode == 'edit'}">
        <h2>Edit Book</h2>
        <c:set var="action" value="/books/${book.id}"/>
    </c:when>
    <c:otherwise>
        <h2>Add Book</h2>
        <c:set var="action" value="/books"/>
    </c:otherwise>
</c:choose>

<form:form modelAttribute="book" action="${pageContext.request.contextPath}${action}" method="post">
    <div class="field">
        <label for="title">Title</label>
        <form:input path="title" id="title"/>
        <form:errors path="title" cssClass="error" element="div"/>
    </div>

    <div class="field">
        <label for="isbn">ISBN (must be unique)</label>
        <form:input path="isbn" id="isbn"/>
        <form:errors path="isbn" cssClass="error" element="div"/>
    </div>

    <div class="field">
        <label for="price">Price (USD)</label>
        <form:input path="price" id="price" type="number" step="0.01"/>
        <form:errors path="price" cssClass="error" element="div"/>
    </div>

    <div class="field">
        <label for="authorId">Author</label>
        <select id="authorId" name="authorId">
            <option value="">-- Select an author --</option>
            <c:forEach var="a" items="${authors}">
                <c:set var="sel" value=""/>
                <c:if test="${(mode == 'edit' and a.id == selectedAuthorId)
                              or (mode != 'edit' and a.id == book.author.id)}">
                    <c:set var="sel" value="selected"/>
                </c:if>
                <option value="${a.id}" ${sel}><c:out value="${a.name}"/></option>
            </c:forEach>
        </select>
        <form:errors path="author" cssClass="error" element="div"/>
    </div>

    <button type="submit" class="btn">
        <c:choose><c:when test="${mode == 'edit'}">Update Book</c:when><c:otherwise>Create Book</c:otherwise></c:choose>
    </button>
    <a class="btn btn-light" href="<c:url value='/books'/>">Cancel</a>
</form:form>

<%@ include file="../common/footer.jsp" %>
