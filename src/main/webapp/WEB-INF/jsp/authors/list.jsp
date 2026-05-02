<%@ include file="../common/header.jsp" %>

<h2>Authors</h2>

<div class="toolbar">
    <span>Total: <strong><c:out value="${authors.size()}"/></strong></span>
    <a class="btn" href="<c:url value='/authors/new'/>">+ Add Author</a>
</div>

<table>
    <thead>
    <tr>
        <th>#</th>
        <th>Name</th>
        <th>Nationality</th>
        <th>Birth Year</th>
        <th># of Books</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="a" items="${authors}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td><c:out value="${a.name}"/></td>
            <td><c:out value="${a.nationality}"/></td>
            <td>${a.birthYear}</td>
            <td>${a.books.size()}</td>
            <td class="actions">
                <a href="<c:url value='/authors/${a.id}/edit'/>">Edit</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty authors}">
        <tr><td colspan="6" style="text-align:center; color:#8898aa;">No authors yet.</td></tr>
    </c:if>
    </tbody>
</table>

<%@ include file="../common/footer.jsp" %>
