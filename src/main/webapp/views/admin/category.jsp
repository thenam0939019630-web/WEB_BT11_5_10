<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<section aria-labelledby="category-heading">
    <div class="section-heading"><div><p class="eyebrow">Danh mục sản phẩm</p><h2 id="category-heading">Category</h2></div></div>
    <form class="admin-create" method="post" action="${pageContext.request.contextPath}/admin/category">
        <input type="hidden" name="action" value="save">
        <input class="wide" name="name" placeholder="Tên danh mục" required>
        <input class="wide" name="images" placeholder="Tên ảnh">
        <input name="status" type="number" value="1" aria-label="Trạng thái">
        <button type="submit">Thêm</button>
    </form>
    <div class="table-wrap">
        <table>
            <thead><tr><th>ID</th><th>Thông tin danh mục</th><th>Thao tác</th></tr></thead>
            <tbody>
            <c:forEach items="${records}" var="category">
                <tr>
                    <td>${category.categoryId}</td>
                    <td>
                        <form class="table-edit" method="post" action="${pageContext.request.contextPath}/admin/category">
                            <input type="hidden" name="action" value="save">
                            <input type="hidden" name="id" value="${category.categoryId}">
                            <input name="name" value="${category.categoryName}" aria-label="Tên danh mục" required>
                            <input name="images" value="${category.images}" aria-label="Tên ảnh">
                            <input name="status" type="number" value="${category.status}" aria-label="Trạng thái">
                            <button class="button-small" type="submit">Sửa</button>
                        </form>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/category">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${category.categoryId}">
                            <button class="button-danger button-small" type="submit">Xóa</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty records}"><tr><td colspan="3"><div class="empty-state">Chưa có danh mục nào.</div></td></tr></c:if>
            </tbody>
        </table>
    </div>
</section>