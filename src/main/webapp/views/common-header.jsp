<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<header class="site-header"><div class="nav-shell">
	<a class="brand" href="${pageContext.request.contextPath}/home"><span class="brand-mark">W</span>WebShop</a>
	<nav class="nav-links"><a class="nav-link" href="${pageContext.request.contextPath}/home">Trang Chủ</a><a class="nav-link" href="${pageContext.request.contextPath}/product">Sản phẩm</a>
		<c:if test="${sessionScope.user.role.roleId == 2}"><a class="nav-link" href="${pageContext.request.contextPath}/cart">Giỏ hàng</a><a class="nav-link" href="${pageContext.request.contextPath}/orders">Lịch sử đặt hàng</a></c:if><c:choose><c:when test="${not empty sessionScope.user}"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></c:when><c:otherwise><a class="nav-link" href="${pageContext.request.contextPath}/login">Đăng nhập</a></c:otherwise></c:choose>
	</nav>
</div></header><main class="container">
