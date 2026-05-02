<%@ taglib prefix="c"    uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt"  uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Library Management</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>">
</head>
<body>
<header>
    <h1>Library Management</h1>
    <nav>
        <a href="<c:url value='/books'/>">Books</a>
        <a href="<c:url value='/authors'/>">Authors</a>
        <a href="<c:url value='/h2-console'/>" target="_blank">H2 Console</a>
    </nav>
</header>
<main>
