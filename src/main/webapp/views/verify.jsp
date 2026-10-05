<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<title>Xác nhận OTP | WebShop</title>
<section class="form-card">
	<p class="eyebrow">Xác minh email</p>
	<h1>Nhập mã OTP</h1>
	<p class="muted">Nhập mã xác nhận đã được gửi tới email đăng ký của bạn.</p>
	<form class="form-grid" action="${pageContext.request.contextPath}/verify-otp" method="post">
		<label class="form-field">Mã OTP 6 chữ số<input name="otp" inputmode="numeric" pattern="[0-9]{6}" maxlength="6" autocomplete="one-time-code" required></label>
		<button type="submit">Xác nhận</button>
	</form>
	<c:if test="${not empty error}"><p class="form-error">${error}</p></c:if>
</section>
