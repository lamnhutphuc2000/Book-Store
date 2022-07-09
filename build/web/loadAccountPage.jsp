<%-- 
    Document   : loadAccountPage
    Created on : Dec 18, 2020, 2:37:10 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Load Users Page</title>
    </head>
    <body>
        <h1>All Accounts</h1>
        <c:set var="listAccounts" value="${requestScope.listAccounts}"/>
        
            <table border="1">
                <thead>
                    <tr>
                        <th>userID</th>
                        <th>name</th>
                        <th>roleID</th>
                        <th>status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="account" items="${listAccounts}" varStatus="counter">
                        <tr>
                            <td>${account.userID}</td>
                            <td>${account.name}</td>
                            <td>${account.roleID}</td>
                            <td>${account.status}</td>
                        </tr>
                    </c:forEach> 
                </tbody>
            </table> 
        <a href="LoadCategoryServlet">Back to store</a>
    </body>
</html>
