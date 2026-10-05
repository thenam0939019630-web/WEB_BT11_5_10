<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<section aria-labelledby="admin-product-heading">
    <div class="section-heading"><div><p class="eyebrow">Kho hàng</p><h2 id="admin-product-heading">Product</h2></div></div>
    <form class="admin-create" method="post" action="${pageContext.request.contextPath}/admin/product">
        <input type="hidden" name="action" value="save">
        <input class="wide" name="name" placeholder="Tên sản phẩm" required>
        <input name="code" type="number" placeholder="Mã sản phẩm" required>
        <input name="price" type="number" step="0.01" placeholder="Giá" required>
        <input name="amount" type="number" placeholder="Amount">
        <input name="stock" type="number" placeholder="Stock">
        <input name="status" type="number" value="1" placeholder="Status" aria-label="Trạng thái">
        <select name="categoryId" aria-label="Danh mục">
            <option value="">Chọn danh mục</option>
            <c:forEach items="${categories}" var="category"><option value="${category.categoryId}">${category.categoryName}</option></c:forEach>
        </select>
        <input name="sellerId" type="number" placeholder="Seller ID">
        <input class="wide" name="images" placeholder="Tên ảnh">
        <input class="wide" name="description" placeholder="Mô tả">
        <button type="submit">Thêm</button>
    </form>
    <div class="table-wrap">
        <table>
            <thead><tr><th>ID</th><th>Thông tin sản phẩm</th><th>Thao tác</th></tr></thead>
            <tbody>
            <c:forEach items="${records}" var="p">
                <tr>
                    <td>${p.productId}</td>
                    <td>
                        <form class="table-edit" method="post" action="${pageContext.request.contextPath}/admin/product">
                            <input type="hidden" name="action" value="save">
                            <input type="hidden" name="id" value="${p.productId}">
                            <input name="name" value="${p.productName}" aria-label="Tên sản phẩm" required>
                            <input name="code" type="number" value="${p.productCode}" aria-label="Mã sản phẩm">
                            <input name="price" type="number" step="0.01" value="${p.price}" aria-label="Giá">
                            <input name="amount" type="number" value="${p.amount}" aria-label="Amount">
                            <input name="stock" type="number" value="${p.stock}" aria-label="Stock">
                            <input name="status" type="number" value="${p.status}" aria-label="Trạng thái">
                            <input name="wishlist" type="number" value="${p.wishlist}" aria-label="Wishlist">
                            <select name="categoryId" aria-label="Danh mục">
                                <option value="">Chưa phân loại</option>
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category.categoryId}" ${p.category.categoryId == category.categoryId ? 'selected' : ''}>${category.categoryName}</option>
                                </c:forEach>
                            </select>
                            <input name="sellerId" type="number" value="${p.seller.sellerId}" placeholder="Seller ID" aria-label="Seller ID">
                            <input name="images" value="${p.images}" placeholder="Tên ảnh" aria-label="Ảnh">
                            <input name="description" value="${p.description}" placeholder="Mô tả" aria-label="Mô tả">
                            <button class="button-small" type="submit">Sửa</button>
                        </form>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/product">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${p.productId}">
                            <button class="button-danger button-small" type="submit">Xóa</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty records}"><tr><td colspan="3"><div class="empty-state">Chưa có sản phẩm nào.</div></td></tr></c:if>
            </tbody>
        </table>
    </div>
</section>