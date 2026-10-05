<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="WebShop - hệ thống bán hàng trực tuyến">
    <title><sitemesh:write property="title" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <sitemesh:write property="head" />
</head>
<body>
<header class="site-header">
    <div class="nav-shell">
        <a class="brand" href="${pageContext.request.contextPath}/home">
            <span class="brand-mark">W</span><span>WebShop</span>
        </a>
        <nav class="nav-links" aria-label="Điều hướng chính">
            <a class="nav-link" href="${pageContext.request.contextPath}/home">Trang Chủ</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/product">Sản phẩm</a>
            <c:if test="${sessionScope.user.role.roleId == 2}"><a class="nav-link" href="${pageContext.request.contextPath}/cart">Giỏ hàng</a><a class="nav-link" href="${pageContext.request.contextPath}/orders">Lịch sử đặt hàng</a></c:if><c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <c:if test="${sessionScope.user.role.roleId == 1}"><a class="nav-link" href="${pageContext.request.contextPath}/admin/category">Quản trị</a></c:if>
                    <a class="nav-link nav-action" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </c:when>
                <c:otherwise><a class="nav-link nav-action" href="${pageContext.request.contextPath}/login">Đăng nhập</a></c:otherwise>
            </c:choose>
        </nav>
    </div>
</header>
<main class="container">
    <sitemesh:write property="body" />
</main>
<footer class="site-footer">
    <div class="footer-inner container">
        <span>Họ tên: Nguyễn Thế Nam</span>
        <span>MSSV: 24110285</span>
        
    </div>
</footer>
</body>
</html>
