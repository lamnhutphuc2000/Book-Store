<%-- 
    Document   : search
    Created on : Dec 8, 2020, 2:59:15 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bookstore</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <h1>Welcome to the bookstore</h1>
        
        <a href="LoadAllAccountServlet">Load all users</a>
        
        <c:set var="account" value="${sessionScope.account}"/>
        <c:set var="role" value="${sessionScope.role}"/>
        <c:if test="${not empty account}">
            <h2>Welcome, ${account.name}</h2>
            <form action="DispatchController">
                <input type="submit" value="Log out" name="btAction" />
            </form>
            <c:if test="${not empty role}">
                <c:if test="${role == 'Admin'}">
                    <form action="DispatchController">
                        <input type="submit" value="Insert new book" name="btAction" />
                        <input type="submit" value="Create discount code" name="btAction" />
                    </form>
                </c:if>
                <c:if test="${role ne 'Admin'}">
                    <form action="DispatchController">
                        <input type="submit" value="Shopping History" name="btAction"/>
                    </form>
                    <c:set var="cart" value="${sessionScope.cart}"/>
                    <c:if  test="${not empty cart}">

                        <c:set var="itemInCart" value="${cart.cart}"/>
                        <c:if test="${not empty itemInCart}">
                            <form action="DispatchController">
                                <input type="submit" value="View cart" name="btAction" />
                            </form>
                        </c:if>
                    </c:if>
                </c:if>
            </c:if>
        </c:if>

        <c:if test="${empty account}">
            <a href="login.html">Log in</a>
        </c:if>

        <c:set var="searchBookError" value="${requestScope.searchBookError}"/>

        <form action="DispatchController">
            Book's name: <input type="text" name="txtBookName" value="${param.txtBookName}" />
            Price from: <input type="text" name="txtBookPriceFrom" value="${param.txtBookPriceFrom}" />

            <c:if test="${not empty searchBookError.priceFromNegativeError}">
                <div class="text-danger">${searchBookError.priceFromNegativeError}</div> </br>
            </c:if>
            <c:if test="${not empty searchBookError.priceFromFormatError}">
                <div class="text-danger">${searchBookError.priceFromFormatError}</div> </br>
            </c:if>


            Price to: <input type="text" name="txtBookPriceTo" value="${param.txtBookPriceTo}" />

            <c:if test="${not empty searchBookError.priceToNegativeError}">
                <div class="text-danger"> ${searchBookError.priceToNegativeError}</div> </br>
            </c:if>
            <c:if test="${not empty searchBookError.priceToFormatError}">
                <div class="text-danger">${searchBookError.priceToFormatError}</div> </br>
            </c:if>
            <c:if test="${not empty searchBookError.priceToSmallerPriceFrom}">
                <div class="text-danger">${searchBookError.priceToSmallerPriceFrom}</div> </br>
            </c:if>

            <c:set var="listCategory" value="${requestScope.listCategory}"/>
            <c:if test="${not empty listCategory}">
                Category: 
                <select name="txtBookCategory"> 
                    <option>All</option> 
                    <c:forEach var="category" step="1" items="${listCategory}">  
                        <c:if test="${param.txtBookCategory == category.categoryName}">
                            <option selected>${category.categoryName}</option>  
                        </c:if>
                        <c:if test="${param.txtBookCategory ne category.categoryName}">
                            <option>${category.categoryName}</option>  
                        </c:if> 

                    </c:forEach>
                </select> 
            </c:if> 
            <c:if test="${empty listCategory}">
                <c:redirect url="LoadCategoryServlet"/>
            </c:if>
            <input type="submit" value="Search Book" name="btAction" />    
        </form> 


        <c:if test="${role == 'Admin'}">
            <c:set var="listBooks" value="${requestScope.listBooks}"/>
            <c:if test="${not empty listBooks}">
                <c:set var="updateBookError" value="${requestScope.updateBookError}"/>
                <c:forEach var="book" step="1" items="${listBooks}">  
                    <c:if test="${book.bookID == updateBookError.updateBookIDError}">
                        <form action="DispatchController">
                            
                            <img src="${book.image}"/></br> 
                            Title:  
                            <input type="text" name="txtUpdateBookTitle" value="${book.title}" /> </br> 
                            <c:if test="${not empty updateBookError.titleLengthError}">
                                <div class="text-danger"> ${updateBookError.titleLengthError} </div> </br>
                            </c:if>
                            Choose another image: <input type="file" name="txtUpdateBookImage" /></br>
                            <input type="hidden" name="txtBookImage" value="${book.image}" />
                            Description: 
                            <input type="text" name="txtUpdateBookDescription" value="${book.description}" /> </br> 

                            <c:if test="${not empty updateBookError.descriptionLengthError}">
                                <div class="text-danger"> ${updateBookError.descriptionLengthError}</div> </br>
                            </c:if>

                            Price: 
                            <input type="text" name="txtUpdateBookPrice" value="${book.price}" /> </br>

                            <c:if test="${not empty updateBookError.priceNegativeError}">
                                <div class="text-danger">${updateBookError.priceNegativeError}</div>  </br>
                            </c:if>
                            <c:if test="${not empty updateBookError.priceEmtpyError}">
                                <div class="text-danger">${updateBookError.priceEmtpyError}</div>  </br>
                            </c:if>
                            <c:if test="${not empty updateBookError.priceFormatError}">
                                <div class="text-danger">${updateBookError.priceFormatError}</div>  </br>
                            </c:if>

                            Author: 
                            <input type="text" name="txtUpdateBookAuthor" value="${book.author}" /> </br>

                            <c:if test="${not empty updateBookError.authorLengthError}">
                                <div class="text-danger">${updateBookError.authorLengthError}</div>  </br>
                            </c:if> 
                            <c:if test="${not empty updateBookError.authorFormatError}">
                                <div class="text-danger">${updateBookError.authorFormatError}</div>  </br>
                            </c:if>

                            Category: 

                            <select name="txtUpdateBookCategory">  
                                <c:forEach var="category" step="1" items="${listCategory}">
                                    <c:if test="${category.categoryID == book.categoryID}">
                                        <option selected>${category.categoryName}</option>  
                                    </c:if>
                                    <c:if test="${category.categoryID ne book.categoryID}">
                                        <option>${category.categoryName}</option>  
                                    </c:if>
                                </c:forEach>
                            </select> 

                            Quantity: 
                            <input type="text" name="txtUpdateBookQuantity" value="${book.quantity}" /> </br>

                            <c:if test="${not empty updateBookError.quantityLengthError}">
                                <div class="text-danger">${updateBookError.quantityLengthError}</div>  </br>
                            </c:if> 
                            <c:if test="${not empty updateBookError.quantityFormatError}">
                                <div class="text-danger">${updateBookError.quantityFormatError}</div>  </br>
                            </c:if> 
                            <c:if test="${not empty updateBookError.quantityNegativeError}">
                                <div class="text-danger">${updateBookError.quantityNegativeError}</div>  </br>
                            </c:if>

                            Status:
                            <select name="txtUpdateBookStatus">  
                                <c:if test="${book.status == 'Active'}">
                                    <option selected>${book.status}</option> 
                                    <option>Inactive</option>
                                </c:if> 
                                <c:if test="${book.status == 'Inactive'}">
                                    <option selected>${book.status}</option> 
                                    <option>Active</option>
                                </c:if> 
                            </select> 
                            <input type="hidden" name="txtUpdateBookID" value="${book.bookID}" />
                            <input type="hidden" name="txtBookPriceFrom" value="${param.txtBookPriceFrom}" />
                            <input type="hidden" name="txtBookPriceTo" value="${param.txtBookPriceTo}" />
                            <input type="hidden" name="txtBookCategory" value="${param.txtBookCategory}" />
                            <input type="hidden" name="txtBookName" value="${param.txtBookName}" />
                            <input type="submit" value="Update this book" name="btAction"/>
                        </form>


                        <c:if test="${book.status ne 'Inactive'}">
                            <form action="DispatchController">
                                <input type="hidden" name="txtBookDeleteID" value="${book.bookID}" />
                                <input type="hidden" name="txtBookPriceFrom" value="${param.txtBookPriceFrom}" />
                                <input type="hidden" name="txtBookPriceTo" value="${param.txtBookPriceTo}" />
                                <input type="hidden" name="txtBookCategory" value="${param.txtBookCategory}" />
                                <input type="hidden" name="txtBookName" value="${param.txtBookName}" />

                                <input type="submit" value="Delete this book" 
                                       onclick="return confirm('Are you sure you want to delete this book?')"
                                       name="btAction"/>
                            </form>
                        </c:if>

                    </c:if>
                    <c:if test="${book.bookID ne updateBookError.updateBookIDError}">
                        <form action="DispatchController">
                            
                            <img src="${book.image}"/></br>
                            Title:  
                            <input type="text" name="txtUpdateBookTitle" value="${book.title}" /> </br> 

                            Choose another image: <input type="file" name="txtUpdateBookImage" /></br>
                            <input type="hidden" name="txtBookImage" value="${book.image}" />
                            Description: 
                            <input type="text" name="txtUpdateBookDescription" value="${book.description}" /> </br> 
                            Price: 
                            <input type="text" name="txtUpdateBookPrice" value="${book.price}" /> </br>
                            Author: 
                            <input type="text" name="txtUpdateBookAuthor" value="${book.author}" /> </br>
                            Category: 

                            <select name="txtUpdateBookCategory">  
                                <c:forEach var="category" step="1" items="${listCategory}">
                                    <c:if test="${category.categoryID == book.categoryID}">
                                        <option selected>${category.categoryName}</option>  
                                    </c:if>
                                    <c:if test="${category.categoryID ne book.categoryID}">
                                        <option>${category.categoryName}</option>  
                                    </c:if>
                                </c:forEach>
                            </select> 

                            Quantity: 
                            <input type="text" name="txtUpdateBookQuantity" value="${book.quantity}" /> </br>

                            Status:
                            <select name="txtUpdateBookStatus">  
                                <c:if test="${book.status == 'Active'}">
                                    <option selected>${book.status}</option> 
                                    <option>Inactive</option>
                                </c:if> 
                                <c:if test="${book.status == 'Inactive'}">
                                    <option selected>${book.status}</option> 
                                    <option>Active</option>
                                </c:if> 
                            </select> 
                            <input type="hidden" name="txtUpdateBookID" value="${book.bookID}" />
                            <input type="hidden" name="txtBookPriceFrom" value="${param.txtBookPriceFrom}" />
                            <input type="hidden" name="txtBookPriceTo" value="${param.txtBookPriceTo}" />
                            <input type="hidden" name="txtBookCategory" value="${param.txtBookCategory}" />
                            <input type="hidden" name="txtBookName" value="${param.txtBookName}" />
                            <input type="submit" value="Update this book" name="btAction"/>
                        </form>



                        <c:if test="${book.status ne 'Inactive'}">
                            <form action="DispatchController">
                                <input type="hidden" name="txtBookDeleteID" value="${book.bookID}" />
                                <input type="hidden" name="txtBookPriceFrom" value="${param.txtBookPriceFrom}" />
                                <input type="hidden" name="txtBookPriceTo" value="${param.txtBookPriceTo}" />
                                <input type="hidden" name="txtBookCategory" value="${param.txtBookCategory}" />
                                <input type="hidden" name="txtBookName" value="${param.txtBookName}" />

                                <input type="submit" value="Delete this book" 
                                       onclick="return confirm('Are you sure you want to delete this book?')"
                                       name="btAction"/>
                            </form>
                        </c:if>
                    </c:if>
                    </br>
                    </br>
                </c:forEach>
            </c:if> 
        </c:if>

        <c:if test="${role ne 'Admin'}">
            <c:set var="listBooks" value="${requestScope.listBooks}"/>
            <c:if test="${not empty listBooks}">

                <c:forEach var="book" step="1" items="${listBooks}">  
                    <img src="${book.image}"/></br> 
                    ${book.image}
                    Title:  ${book.title}</br> 
                    Description: ${book.description} </br> 
                    Price: ${book.price} $ </br> 
                    Author: ${book.author} </br> 

                    <c:forEach var="category" step="1" items="${listCategory}">
                        <c:if test="${category.categoryID == book.categoryID}"> 
                            Category: ${category.categoryName} </br> 
                        </c:if>  
                    </c:forEach>
                    Quantity: ${book.quantity} </br>    

                    <c:if test="${not empty role}"> 
                        <form action="DispatchController">
                            <input type="hidden" name="txtBookPriceFrom" value="${param.txtBookPriceFrom}" />
                            <input type="hidden" name="txtBookPriceTo" value="${param.txtBookPriceTo}" />
                            <input type="hidden" name="txtBookCategory" value="${param.txtBookCategory}" />
                            <input type="hidden" name="txtBookName" value="${param.txtBookName}" />
                            <input type="hidden" name="txtBookIDToCart" value="${book.bookID}" /> </br>
                            <input type="hidden" name="txtBookTitleToCart" value="${book.title}" /> </br>
                            <input type="hidden" name="txtBookPriceToCart" value="${book.price}" /> </br>
                            <input type="submit" value="Add book to cart" name="btAction"/>
                        </form>
                    </c:if>
                </c:forEach>
            </c:if> 
        </c:if>




    </body>
</html>
