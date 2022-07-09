<%-- 
    Document   : insertNewBookPage
    Created on : Dec 11, 2020, 8:16:47 AM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new Book</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <c:set var="account" value="${sessionScope.account}"/>
        <c:set var="role" value="${sessionScope.role}"/>
        <c:if test="${not empty account}"> 
            <c:if test="${not empty role}">
                <c:if test="${role == 'Admin'}">

                    <c:set var="createNewError" value="${requestScope.createNewError}"/>

                    <form action="DispatchController">
                        ID: <input type="text" name="txtInsertBookID" value="${param.txtInsertBookID}" /></br>
                        <c:if test="${not empty createNewError.bookIDDuplicateError}">
                            <div class="text-danger">${createNewError.bookIDDuplicateError}</div> </br>
                        </c:if>
                        <c:if test="${not empty createNewError.bookIDLengthError}">
                            <div class="text-danger">${createNewError.bookIDLengthError}</div> </br>
                        </c:if>
                        Title: <input type="text" name="txtInsertBookTitle" value="${param.txtInsertBookTitle}" /> </br>
                        <c:if test="${not empty createNewError.titleLengthError}">
                            <div class="text-danger">${createNewError.titleLengthError}</div> </br>
                        </c:if>
                        Image: <input type="file" name="txtInsertBookImage" /></br>
                        <c:if test="${not empty createNewError.imageEmptyError}">
                            <div class="text-danger">${createNewError.imageEmptyError}</div> </br>
                        </c:if>
                        Description: <input type="text" name="txtInsertBookDescription" value="${param.txtInsertBookDescription}" /> </br>
                        <c:if test="${not empty createNewError.descriptionLengthError}">
                            <div class="text-danger">${createNewError.descriptionLengthError}</div> </br>
                        </c:if>
                        Price: <input type="text" name="txtInsertBookPrice" value="${param.txtInsertBookPrice}" /></br>
                        <c:if test="${not empty createNewError.priceNegativeError}">
                            <div class="text-danger">${createNewError.priceNegativeError}</div> </br>
                        </c:if>
                        <c:if test="${not empty createNewError.priceEmtpyError}">
                            <div class="text-danger">${createNewError.priceEmtpyError}</div> </br>
                        </c:if>
                        <c:if test="${not empty createNewError.priceFormatError}">
                            <div class="text-danger">${createNewError.priceFormatError}</div> </br>
                        </c:if>
                        Author: <input type="text" name="txtInsertBookAuthor" value="${param.txtInsertBookAuthor}" /></br>
                        <c:if test="${not empty createNewError.authorLengthError}">
                            <div class="text-danger">${createNewError.authorLengthError}</div> </br>
                        </c:if>
                        <c:if test="${not empty createNewError.authorFormatError}">
                            <div class="text-danger">${createNewError.authorFormatError}</div> </br>
                        </c:if>

                        <c:set var="listCategory" value="${requestScope.listCategory}"/>
                        <c:if test="${not empty listCategory}">
                            Category: 
                            <select name="txtInsertBookCategory">  
                                <c:forEach var="category" step="1" items="${listCategory}">  
                                    <option>${category.categoryName}</option>  
                                </c:forEach>
                            </select>  
                        </c:if> 
                        </br>
                        Quantity: <input type="text" name="txtInsertBookQuantity" value="${param.txtInsertBookQuantity}" /></br>
                        <c:if test="${not empty createNewError.quantityLengthError}">
                            <div class="text-danger">${createNewError.quantityLengthError}</div> </br>
                        </c:if>
                        <c:if test="${not empty createNewError.quantityFormatError}">
                            <div class="text-danger">${createNewError.quantityFormatError}</div> </br>
                        </c:if>
                        <c:if test="${not empty createNewError.quantityNegativeError}">
                            <div class="text-danger">${createNewError.quantityNegativeError}</div> </br>
                        </c:if>
                        <input type="submit" value="Create new book" name="btAction" /> 
                    </form>
                    <a href="LoadCategoryServlet">Back to Store</a>
                </c:if>
                <c:if test="${role ne 'Admin'}">
                    <c:redirect url="LoadCategoryServlet"/>
                </c:if>
            </c:if>
        </c:if>
        <c:if test="${empty account}">
            <c:redirect url="LoadCategoryServlet"/>
        </c:if>
    </body>
</html>
