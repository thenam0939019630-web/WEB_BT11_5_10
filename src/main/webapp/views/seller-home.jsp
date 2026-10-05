<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<p class="eyebrow">Không gian của bạn</p>
<h1 class="page-heading">Kênh người bán</h1>
<p>Mã cửa hàng: ${sessionScope.user.seller.sellerId}</p>
<h3>Sản phẩm của cửa hàng</h3>
<c:choose>
    <c:when test="${empty products}">
        <p>Cửa hàng chưa có sản phẩm.</p>
    </c:when>
    <c:otherwise>
        <div class="table-wrap"><table>
            <thead><tr><th>Mã</th><th>Tên</th><th>Danh mục</th><th>Giá</th><th>Amount</th></tr></thead>
            <tbody>
            <c:forEach items="${products}" var="p">
                <tr>
                    <td>${p.productCode}</td>
                    <td><a href="${pageContext.request.contextPath}/product-detail?id=${p.productId}">${p.productName}</a></td>
                    <td><c:choose><c:when test="${not empty p.category}">${p.category.categoryName}</c:when><c:otherwise>Chưa phân loại</c:otherwise></c:choose></td>
                    <td>${p.price}</td>
                    <td>${p.amount}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table></div>
    </c:otherwise>
</c:choose>