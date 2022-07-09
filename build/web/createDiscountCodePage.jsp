<%-- 
    Document   : createDiscountCodePage
    Created on : Dec 12, 2020, 4:33:33 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Discount Code</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <c:set var="role" value="${sessionScope.role}"/>
        <c:if test="${not empty role}">
            <c:if test="${role == 'Admin'}">
                <c:set var="createNewError" value="${requestScope.createNewError}"/>
        <h1>Create discount code</h1>
        <form action="DispatchController">
            Discount ID: <input type="text" name="txtDiscountID" value="${param.txtDiscountID}" /></br>
            <c:if test="${not empty createNewError.discountIDDuplicateError}">
                <div class="text-danger"> ${createNewError.discountIDDuplicateError}  </div> </br>
            </c:if>
            <c:if test="${not empty createNewError.discountIDLengthError}">
                <div class="text-danger"> ${createNewError.discountIDLengthError}  </div> </br>
            </c:if>
            Discount Percent:
            <select name="txtDiscountPercent"> 
                <option>10</option>  
                <option>20</option>  
                <option>30</option>  
                <option>40</option>  
                <option>50</option>  
            </select> 
            %</br>
            Date: <input type="text" name="txtDiscountDate" value="${param.txtDiscountDate}" /></br>
            <c:if test="${not empty createNewError.dateBeforeTodayError}">
                <div class="text-danger"> ${createNewError.dateBeforeTodayError}  </div> </br>
            </c:if>
            <c:if test="${not empty createNewError.dateFormatError}">
                <div class="text-danger"> ${createNewError.dateFormatError}  </div> </br>
            </c:if>
            <input type="submit" value="Create" name="btAction" />
        </form>
            </c:if>
        </c:if>
        <c:if test="${empty role}">
            <c:redirect url="LoadCategoryServlet"/>
        </c:if>
        <c:if test="${role ne 'Admin'}">
            <c:redirect url="LoadCategoryServlet"/>
        </c:if>
        Back to <a href="LoadCategoryServlet">store</a> !
    </body>
</html>
