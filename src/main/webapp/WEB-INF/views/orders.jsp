<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<h1>Lịch sử đặt hàng</h1>
<c:if test="${not empty message}"><p class="shop-message" role="status"><c:out value="${message}"/></p></c:if>
<form method="get" action="${pageContext.request.contextPath}/orders" class="shop-inline">
<label for="status">Trạng thái</label><select id="status" name="status"><option value="">Tất cả đơn hàng</option>
<c:forEach items="${statuses}" var="status"><option value="${status.key}" ${selectedStatus == status.key ? 'selected' : ''}><c:out value="${status.value}"/></option></c:forEach></select><button class="button" type="submit">Lọc đơn hàng</button></form>
<c:if test="${empty orders}"><p class="empty-state">Không có đơn hàng phù hợp.</p></c:if>
<c:forEach items="${orders}" var="order"><article class="shop-panel">
<h2>Đơn #${order.cartId} — <c:out value="${order.statusLabel}"/></h2>
<p>Ngày đặt: <fmt:formatDate value="${order.buyDate}" pattern="dd/MM/yyyy HH:mm"/> · <c:out value="${order.paymentMethod}"/> (thanh toán khi nhận hàng)</p>
<p>Người nhận: <c:out value="${order.receiverName}"/> · <c:out value="${order.receiverPhone}"/></p><p>Địa chỉ: <c:out value="${order.shippingAddress}"/></p>
<div class="shop-table-wrap"><table class="shop-table"><thead><tr><th>Sản phẩm</th><th>Đơn giá lúc đặt</th><th>Số lượng</th><th>Thành tiền</th></tr></thead><tbody>
<c:forEach items="${order.items}" var="item"><tr><td><c:out value="${item.productName}"/></td><td><fmt:formatNumber value="${item.unitPrice}"/> ₫</td><td>${item.quantity}</td><td><fmt:formatNumber value="${item.subtotal}"/> ₫</td></tr></c:forEach>
</tbody></table></div><p class="product-price">Tổng cộng: <fmt:formatNumber value="${order.total}"/> ₫</p></article></c:forEach>
