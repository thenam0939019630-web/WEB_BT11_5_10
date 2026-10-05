<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<section class="hero">
	<p class="eyebrow">Mua sắm dễ dàng, mỗi ngày</p>
	<h1>WebShop</h1>
	<p>Chào mừng bạn đến với hệ thống bán hàng WebShop. Khám phá sản phẩm từ các cửa hàng và tìm lựa chọn phù hợp với bạn.</p>
	<div><a class="button" href="${pageContext.request.contextPath}/product">Khám phá sản phẩm</a></div>
</section>

<section class="welcome-panel">
	<p><strong>Xin chào:</strong>
		<c:choose>
			<c:when test="${not empty sessionScope.user.fullname}">${sessionScope.user.fullname}</c:when>
			<c:otherwise>Khách hàng</c:otherwise>
		</c:choose>
	</p>
	<a class="button button-secondary" href="${pageContext.request.contextPath}/product">Xem danh sách sản phẩm</a>
</section>

<section class="section-heading">
	<div><p class="eyebrow">WebShop</p><h2>Chào mừng bạn đến với hệ thống bán hàng WebShop</h2></div>
</section>
