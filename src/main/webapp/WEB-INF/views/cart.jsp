<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<h1>Giỏ hàng</h1>
<c:if test="${not empty error}"><p class="shop-error" role="alert"><c:out value="${error}"/></p></c:if>
<c:if test="${not empty message}"><p class="shop-message" role="status"><c:out value="${message}"/></p></c:if>
<c:choose><c:when test="${empty cart.items}"><p class="empty-state">Giỏ hàng đang trống.</p></c:when><c:otherwise>
<div class="shop-table-wrap"><table class="shop-table"><thead><tr><th>Sản phẩm</th><th>Đơn giá</th><th>Số lượng / tồn kho</th><th>Thành tiền</th><th>Thao tác</th></tr></thead><tbody>
<c:forEach items="${cart.items}" var="item"><tr>
<td><a href="${pageContext.request.contextPath}/product-detail?id=${item.product.productId}"><c:out value="${item.product.productName}"/></a>
<c:if test="${item.product.status != 1 or item.product.stock < item.quantity}"><p class="shop-error">Không đủ hàng hoặc ngừng bán. Hãy sửa số lượng hoặc xóa.</p></c:if>
<c:if test="${item.unitPrice != item.product.price}"><p class="shop-error">Giá đã đổi. Nhấn Cập nhật để lấy giá hiện tại: <fmt:formatNumber value="${item.product.price}"/> ₫.</p></c:if></td>
<td><fmt:formatNumber value="${item.unitPrice}"/> ₫</td>
<td><form method="post" action="${pageContext.request.contextPath}/cart" class="shop-inline">
<input type="hidden" name="token" value="${sessionScope.shoppingToken}"><input type="hidden" name="action" value="update"><input type="hidden" name="productId" value="${item.product.productId}">
<input aria-label="Số lượng" type="number" name="quantity" min="1" max="${item.product.stock}" value="${item.quantity}" required> / <c:out value="${item.product.stock}"/>
<button class="button button-small" type="submit">Cập nhật</button></form></td>
<td><fmt:formatNumber value="${item.subtotal}"/> ₫</td>
<td><form method="post" action="${pageContext.request.contextPath}/cart"><input type="hidden" name="token" value="${sessionScope.shoppingToken}"><input type="hidden" name="action" value="remove"><input type="hidden" name="productId" value="${item.product.productId}"><button class="button button-secondary" type="submit">Xóa</button></form></td>
</tr></c:forEach></tbody></table></div>
<p class="product-price">Tổng cộng: <fmt:formatNumber value="${cart.total}"/> ₫</p>
<a class="button" href="${pageContext.request.contextPath}/checkout">Thanh toán COD</a>
</c:otherwise></c:choose>
<a class="button button-secondary" href="${pageContext.request.contextPath}/product">Tiếp tục mua hàng</a>
