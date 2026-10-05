<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="sitemesh" uri="http://www.sitemesh.org/sitemesh3" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>



<html lang="vi">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<title>

<sitemesh:write property="title" />

</title>


<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">


</head>



<body>


<!-- HEADER -->

<nav class="navbar navbar-dark bg-dark">


<div class="container">


<a class="navbar-brand"
href="${pageContext.request.contextPath}/home">

Trang Chủ

</a>



<a class="navbar-brand"
href="${pageContext.request.contextPath}/product">

Sản phẩm

</a>



<c:choose><c:when test="${not empty sessionScope.user}"><a class="navbar-brand" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></c:when><c:otherwise><a class="navbar-brand" href="${pageContext.request.contextPath}/login">Đăng nhập</a></c:otherwise></c:choose>



<c:if test="${sessionScope.user.role.roleId==1}">


<a class="navbar-brand"
href="${pageContext.request.contextPath}/admin">

Trang quản trị

</a>


</c:if>



</div>


</nav>



<!-- BODY -->

<div class="container mt-4">


<sitemesh:write property="body" />


</div>



<!-- FOOTER -->


<footer class="site-footer">


<p>Họ tên: Nguyễn Thế Nam</p><p>MSSV: 24110285</p><p>


</footer>



</body>

</html>