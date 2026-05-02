<%@ include file="common/header.jsp" %>

<div class="error-page">
    <h2><c:out value="${title != null ? title : 'Error'}"/></h2>
    <p><c:out value="${message}"/></p>
    <c:if test="${not empty detail}">
        <pre><c:out value="${detail}"/></pre>
    </c:if>
    <a class="btn" href="<c:url value='/books'/>">Back to Books</a>
</div>

<%@ include file="common/footer.jsp" %>
