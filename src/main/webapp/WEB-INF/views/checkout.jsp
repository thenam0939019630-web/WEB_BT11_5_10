<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<h1>Thanh toán đơn hàng</h1>
<c:if test="${not empty error}"><p class="shop-error" role="alert"><c:out value="${error}"/></p></c:if>
<section class="shop-panel"><h2>Sản phẩm đặt mua</h2>
<c:forEach items="${cart.items}" var="item"><p><c:out value="${item.product.productName}"/> × ${item.quantity} — <fmt:formatNumber value="${item.subtotal}"/> ₫</p></c:forEach>
<p class="product-price">Tổng thanh toán: <fmt:formatNumber value="${cart.total}"/> ₫</p><p>Phí giao hàng: 0 ₫. Phương thức: COD — thanh toán tiền mặt khi nhận hàng.</p></section>
<form method="post" action="${pageContext.request.contextPath}/checkout" class="shop-panel shop-form">
<input type="hidden" name="token" value="${sessionScope.shoppingToken}"><input type="hidden" name="cartId" value="${cart.cartId}">
<label for="receiverName">Họ tên người nhận</label><input id="receiverName" name="receiverName" maxlength="100" required value="<c:out value='${param.receiverName != null ? param.receiverName : sessionScope.user.fullname}'/>">
<label for="receiverPhone">Số điện thoại</label><input id="receiverPhone" name="receiverPhone" type="tel" pattern="\+?[0-9]{9,15}" maxlength="20" required value="<c:out value='${param.receiverPhone != null ? param.receiverPhone : sessionScope.user.phone}'/>">
<label for="shippingAddress">Địa chỉ nhận hàng</label><textarea id="shippingAddress" name="shippingAddress" maxlength="500" rows="3" required><c:out value="${param.shippingAddress}"/></textarea>
<button class="button" type="submit">Xác nhận đặt hàng COD</button><a href="${pageContext.request.contextPath}/cart">Quay lại giỏ hàng</a></form>
