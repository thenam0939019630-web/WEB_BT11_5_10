<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<title>Đăng nhập | WebShop</title>
<section class="form-card">
       <p class="eyebrow">Chào mừng trở lại</p>
       <h1>Đăng nhập</h1>
       <c:if test="${param.registered == '1'}"><p class="form-success">Đăng ký thành công. Hãy đăng nhập.</p></c:if>
       <form class="form-grid" action="${pageContext.request.contextPath}/login" method="post">
              <label class="form-field">Tên đăng nhập<input type="text" name="username" autocomplete="username" required></label>
              <label class="form-field">Mật khẩu<input type="password" name="password" autocomplete="current-password" required></label>
              <div class="form-actions"><button type="submit">Đăng nhập</button><a class="button button-secondary" href="${pageContext.request.contextPath}/register">Tạo tài khoản</a></div>
       </form>
       <c:if test="${not empty error}"><p class="form-error">${error}</p></c:if>
</section>
