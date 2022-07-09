<%-- 
    Document   : cartPage
    Created on : Dec 12, 2020, 3:41:52 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Cart</title>
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
        <c:set var="cart" value="${sessionScope.cart}"/> 
        <c:if test="${not empty cart}">
            <c:set var="itemInCart" value="${cart.cart}"/>
            <c:if test="${not empty itemInCart}">


                <h1>Your Cart</h1>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Title</th>
                            <th>Quantity</th>
                            <th>Prices</th>
                            <th>Sum</th>
                            <th>Update quantity</th>
                            <th>Removes</th>
                        </tr>
                    </thead>
                    <c:set var="sumOfMoney" value="0"/>

                    <tbody>
                        <c:set var="updateQuantityError" value="${requestScope.updateQuantityError}"/>
                        <c:forEach var="item" items="${itemInCart}" varStatus="counter">
                        <form action="DispatchController">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${item.title}</td>
                                <td><input type="text" name="txtQuantity" value="${item.quantity}" /></td>
                                    <c:if test="${updateQuantityError.bookID == item.bookID}"> 
                                        <c:if test="${not empty updateQuantityError.quantityFormatError}">
                                    <div class="text-danger">${updateQuantityError.quantityFormatError} </div>
                                </c:if>
                                <c:if test="${not empty updateQuantityError.quantityEmptyError}">
                                    <div class="text-danger">${updateQuantityError.quantityEmptyError} </div>
                                </c:if>
                                <c:if test="${not empty updateQuantityError.quantityNegativeError}">
                                    <div class="text-danger">${updateQuantityError.quantityNegativeError} </div>
                                </c:if>
                                <c:if test="${not empty updateQuantityError.bookOutOfStockError}">
                                    <div class="text-danger">${updateQuantityError.bookOutOfStockError} </div>
                                </c:if>
                            </c:if> 

                            <td>${item.price} $</td>
                            <td>${item.price * item.quantity} $</td>
                            <c:set var="sumOfMoney" value="${sumOfMoney + item.price * item.quantity}"/>
                            <input type="hidden" name="txtBookID" value="${item.bookID}" />

                            <td> <input type="submit" value="Update quantity" name="btAction"/> </td>
                            <td> 
                                <input type="submit" value="Remove this boook" onclick="return confirm('Are you sure you want to remove this book?')" name="btAction" />
                            </td>
                            </tr> 
                        </form>
                    </c:forEach> 
                </tbody> 

            </table>
            <h3>Total: ${sumOfMoney} $</h3>

            <form action="DispatchController">
                <input type="hidden" name="txtTotalPrice" value="${sumOfMoney}" />
                Discount code (if any) : <input type="text" name="txtDiscountCode" value="${param.txtDiscountCode}" />
                <input type="submit" value="Confirm" name="btAction"/>
            </form> 
            <c:set var="checkoutError" value="${requestScope.checkoutError}"/>
            <c:if test="${not empty checkoutError.discountCodeNoExistError}">
                <div class="text-danger"> ${checkoutError.discountCodeNoExistError} </div> </br>
            </c:if>
            <c:if test="${not empty checkoutError.discountCodeAlreadyUsedError}">
                <div class="text-danger"> ${checkoutError.discountCodeAlreadyUsedError} </div> </br>
            </c:if>
            <c:if test="${not empty checkoutError.discountCodeIsExpiredError}">
                <div class="text-danger"> ${checkoutError.discountCodeIsExpiredError} </div> </br>
            </c:if>
            <c:if test="${not empty checkoutError.bookIsOutOfStockError}">
                <div class="text-danger"> ${checkoutError.bookIsOutOfStockError} </div> </br>
            </c:if>
        </c:if>
    </c:if>
    <c:if test="${ empty itemInCart}">
        <h1>Your cart is empty</h1>
    </c:if>
    Back to <a href="LoadCategoryServlet">store</a> !
</body>
</html>
