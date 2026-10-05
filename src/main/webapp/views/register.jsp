<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<title>Đăng ký | WebShop</title>
<section class="form-card">
	<p class="eyebrow">Thành viên mới</p>
	<h1>Tạo tài khoản</h1>
	<form class="form-grid" action="${pageContext.request.contextPath}/register" method="post">
		<label class="form-field">Tên đăng nhập<input name="username" value="<c:out value='${param.username}'/>" autocomplete="username" required></label>
		<label class="form-field">Email<input name="email" value="<c:out value='${param.email}'/>" type="email" autocomplete="email" required></label>
		<label class="form-field">Họ và tên<input name="fullname" value="<c:out value='${param.fullname}'/>" autocomplete="name" required></label>
		<label class="form-field">Mật khẩu<input type="password" name="password" autocomplete="new-password" required></label>
		<div class="form-actions"><button type="submit">Đăng ký và nhận OTP</button><a href="${pageContext.request.contextPath}/login">Đã có tài khoản?</a></div>
	</form>
	<c:if test="${not empty error}"><p class="form-error">${error}</p></c:if>
</section>
