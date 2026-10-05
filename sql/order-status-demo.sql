USE WebShop05;
GO
SELECT cartId, userId, buyDate, status, receiverName, paymentMethod
FROM dbo.Cart WHERE status BETWEEN 1 AND 8 ORDER BY cartId DESC;

-- Thay NULL bằng mã đơn đã đặt. Chạy UPDATE rồi tải lại /orders và chọn bộ lọc.
DECLARE @OrderId INT = NULL;
DECLARE @Status INT = 2;
-- 1 Đơn hàng mới | 2 Đã xác nhận | 3 Chuẩn bị hàng | 4 Vận chuyển
-- 5 Giao hàng | 6 Đã giao | 7 Đơn hàng hủy | 8 Đơn hàng hoàn
IF @OrderId IS NOT NULL AND @Status BETWEEN 1 AND 8
    UPDATE dbo.Cart SET status = @Status WHERE cartId = @OrderId AND status BETWEEN 1 AND 8;
-- Script chỉ mô phỏng trạng thái để quan sát; không thực hiện nghiệp vụ hoàn tiền/hoàn tồn kho.
