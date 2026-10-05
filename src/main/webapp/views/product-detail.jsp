<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<p class="eyebrow">Thông tin sản phẩm</p>
<section class="detail-layout">
	<div class="detail-image">
		<c:choose>
			<c:when test="${not empty product.images}"><img src="${pageContext.request.contextPath}/images/${product.images}" alt="${product.productName}"></c:when>
			<c:otherwise><span class="product-placeholder">WebShop</span></c:otherwise>
		</c:choose>
	</div>
	<div class="detail-copy">
		<h1>${product.productName}</h1>
		<p class="product-price">${product.price} ₫</p>
		<p class="muted">Mã sản phẩm: ${product.productCode}</p>
		<p class="muted">Danh mục: <c:choose><c:when test="${not empty product.category}">${product.category.categoryName}</c:when><c:otherwise>Chưa phân loại</c:otherwise></c:choose></p>
		<p>Số lượng: ${product.amount}</p>
		<p>Tồn kho: ${product.stock}</p>
<c:choose><c:when test="${product.status == 1 and product.stock > 0}">
<form method="post" action="${pageContext.request.contextPath}/cart" class="shop-inline">
<input type="hidden" name="token" value="${sessionScope.shoppingToken}">
<input type="hidden" name="action" value="add"><input type="hidden" name="productId" value="${product.productId}">
<label for="quantity">Số lượng</label><input id="quantity" name="quantity" type="number" min="1" max="${product.stock}" value="1" required>
<button type="submit" class="button">Thêm vào giỏ hàng</button>
</form></c:when><c:otherwise><p class="shop-error">Sản phẩm hết hàng hoặc ngừng bán.</p></c:otherwise></c:choose><h2>Mô tả</h2>
		<p>${product.description}</p>
		<a class="button button-secondary" href="${pageContext.request.contextPath}/product">Quay lại sản phẩm</a>
	</div>
</section>
