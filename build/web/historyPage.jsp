<%-- 
    Document   : historyPage
    Created on : Dec 12, 2020, 7:09:49 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping History</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <c:set var="role" value="${sessionScope.role}"/>
        <c:if test="${empty role}">
            <c:redirect url="LoadCategoryServlet"/>
        </c:if>
        <c:if test="${role == 'Admin'}">
            <c:redirect url="LoadCategoryServlet"/>
        </c:if>

        <c:set var="shoppingHistoryError" value="${requestScope.shoppingHistoryError}"/>
        <h1>This is your Shopping History</h1>
        <form action="DispatchController">
            Name: <input type="text" name="txtShoppingHistoryTitle" value="${param.txtHistoryTitle}" /> </br>
            Shopping date : <input type="text" name="txtShoppingHistoryDate" value="${param.txtShoppingHistoryDate}" /> </br>
            <c:if test="${not empty shoppingHistoryError.dateFormatError}">
                <div class="text-danger"> ${shoppingHistoryError.dateFormatError}  </div> </br>
            </c:if>
            <input type="submit" value="Search history" name="btAction" />
        </form>
        Back to           <a href="LoadCategoryServlet">Store</a>
        <c:set var="listOrder" value="${requestScope.listOrder}"/>
        <c:set var="listOrderDetail" value="${requestScope.listOrderDetail}"/>
         
              
        
        <c:forEach var="order" items="${listOrder}" varStatus="counter"> 
            <c:forEach var="orderDetail" items="${listOrderDetail}" varStatus="counter">
            <c:if test="${orderDetail.orderID == order.orderID}">
            <div> OderID:  ${order.orderID}  </div> 
            <div>  totalPrice: ${order.totalPrice}  $   </div> 
            <div>  date: ${order.date}  </div> </br>
            Order detail
            </c:if>
            
                <c:if test="${orderDetail.orderID == order.orderID}"> 
                    <div> Title: ${orderDetail.bookTitle}  </div> 
                    <div> Quantity:  ${orderDetail.quantity}  </div> 
                    <div> Price: ${orderDetail.price}  $</div> 
                </c:if>
                    </br>
            </c:forEach>
            </br>
            </br>
            </br>
        </c:forEach> 

    </body>
</html>
