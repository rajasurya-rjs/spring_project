<%@ include file="../common/header.jsp" %>

<h2>Books</h2>

<p style="color:#5a6a80; font-size:13px; margin-top:-6px;">
    The list below is produced by a custom <strong>INNER JOIN</strong> JPQL query
    that joins <code>Book</code> with <code>Author</code> and projects into a DTO.
</p>

<div class="toolbar">
    <span>Total: <strong><c:out value="${books.size()}"/></strong></span>
    <a class="btn" href="<c:url value='/books/new'/>">+ Add Book</a>
</div>

<table>
    <thead>
    <tr>
        <th>#</th>
        <th>Title</th>
        <th>ISBN</th>
        <th>Price</th>
        <th>Author</th>
        <th>Nationality</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="b" items="${books}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td><c:out value="${b.title}"/></td>
            <td><c:out value="${b.isbn}"/></td>
            <td>$<fmt:formatNumber value="${b.price}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td><c:out value="${b.authorName}"/></td>
            <td><c:out value="${b.authorNationality}"/></td>
            <td class="actions">
                <a href="<c:url value='/books/${b.bookId}/edit'/>">Edit</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty books}">
        <tr><td colspan="7" style="text-align:center; color:#8898aa;">No books yet.</td></tr>
    </c:if>
    </tbody>
</table>

<%@ include file="../common/footer.jsp" %>
