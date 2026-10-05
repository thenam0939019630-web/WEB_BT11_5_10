<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<p class="eyebrow">Cửa hàng WebShop</p>
<h1 class="page-heading">Sản phẩm theo cửa hàng</h1>
<c:choose>
    <c:when test="${empty sellerProducts}">
        <div class="empty-state">Hiện chưa có sản phẩm để hiển thị.</div>
    </c:when>
    <c:otherwise>
        <c:forEach items="${sellerProducts}" var="sellerGroup">
            <section class="seller-section">
                <h2 class="seller-title">Mã cửa hàng: ${sellerGroup.key}</h2>
                <div class="product-grid">
                    <c:forEach items="${sellerGroup.value}" var="p">
                        <article class="product-card">
                            <a class="product-image" href="${pageContext.request.contextPath}/product-detail?id=${p.productId}" aria-label="Xem ${p.productName}">
                                <c:choose>
                                    <c:when test="${not empty p.images}"><img src="${pageContext.request.contextPath}/images/${p.images}" alt="${p.productName}"></c:when>
                                    <c:otherwise><span class="product-placeholder">W</span></c:otherwise>
                                </c:choose>
                            </a>
                            <div class="product-info">
                                <h3><a href="${pageContext.request.contextPath}/product-detail?id=${p.productId}">${p.productName}</a></h3>
                                <div class="product-meta">
                                    <span>Mã sản phẩm: ${p.productCode}</span>
                                    <span>Danh mục: <c:choose><c:when test="${not empty p.category}">${p.category.categoryName}</c:when><c:otherwise>Chưa phân loại</c:otherwise></c:choose></span>
                                    <span>Amount: ${p.amount}</span>
                                </div>
                                <p class="product-price">${p.price} ₫</p>
                                <div class="product-actions"><a class="button button-small" href="${pageContext.request.contextPath}/product-detail?id=${p.productId}">Xem chi tiết</a></div>
                            </div>
                        </article>
                    </c:forEach>
                </div>
            </section>
        </c:forEach>
    </c:otherwise>
</c:choose>
