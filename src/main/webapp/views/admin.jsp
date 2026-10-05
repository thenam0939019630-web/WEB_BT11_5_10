<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<p class="eyebrow">Không gian quản trị</p>
<h1 class="page-heading">Quản lý cửa hàng</h1>
<nav class="admin-nav" aria-label="Các khu vực quản trị">
    <a class="${type == 'category' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/category">Quản trị Category</a>
    <a class="${type == 'product' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/product">Quản trị Product</a>
</nav>

<c:choose>
    <c:when test="${type == 'category'}"><jsp:include page="admin/category.jsp" /></c:when>
    <c:otherwise><jsp:include page="admin/product.jsp" /></c:otherwise>
</c:choose>

<nav class="pagination" aria-label="Phân trang">
    <c:if test="${page > 1}"><a class="button button-secondary button-small" href="${pageContext.request.contextPath}/admin/${type}?page=${page - 1}">Trang trước</a></c:if>
    <span>Trang ${page} / ${pageCount}</span>
    <c:if test="${page < pageCount}"><a class="button button-secondary button-small" href="${pageContext.request.contextPath}/admin/${type}?page=${page + 1}">Trang sau</a></c:if>
</nav>