<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Quản trị hệ thống WebShop">
    <title><sitemesh:write property="title" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <sitemesh:write property="head" />
</head>
<body>
<header class="site-header">
    <div class="nav-shell">
        <a class="brand" href="${pageContext.request.contextPath}/home">
            <span class="brand-mark">W</span><span>WebShop <span class="muted">/ Quản trị</span></span>
        </a>
        <nav class="nav-links" aria-label="Điều hướng quản trị">
            <a class="nav-link" href="${pageContext.request.contextPath}/home">Trang Chủ</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/product">Sản phẩm</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/admin/category">Quản trị Category</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/admin/product">Quản trị Product</a>
            <a class="nav-link nav-action" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
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
