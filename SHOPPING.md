# Giỏ hàng, COD và lịch sử đơn hàng

1. Database đã có dữ liệu: chạy `sql/shopping-migration.sql` trong SQL Server Management Studio. Database mới: chạy `data.sql`, sau đó chạy migration. Không chạy lại `data.sql` trên database hiện có.
2. Build bằng `mvn package`, triển khai `target/WebShop.war` lên Tomcat 11 và đăng nhập tài khoản roleId = 2 (User).
3. Vào chi tiết sản phẩm → chọn số lượng → Thêm vào giỏ hàng. Trong giỏ có cập nhật số lượng, xóa, tổng tiền và liên kết thanh toán.
4. Nhập thông tin nhận hàng và xác nhận COD. Đơn chuyển sang trạng thái mới; tồn kho giảm và giỏ hàng đang mua trở thành trống. Giỏ mới được tạo ở lần thêm tiếp theo.
5. Vào Lịch sử đặt hàng; dùng `sql/order-status-demo.sql` thay trạng thái một đơn rồi tải lại trang, lọc lần lượt các trạng thái 1–8.

## Quy ước

- `Product.stock` là tồn kho khả dụng; số lượng hợp lệ từ 1 đến stock. `amount` giữ nguyên ý nghĩa cũ của project.
- `Cart.status = 0` là giỏ đang mua; 1 mới, 2 xác nhận, 3 chuẩn bị, 4 vận chuyển, 5 giao hàng, 6 đã giao, 7 hủy, 8 hoàn.
- Giá và tên sản phẩm được chụp lại khi đặt; tổng tiền tính bằng BigDecimal từ đơn giá hiện có. COD không được coi là đã thu tiền khi vừa đặt đơn.
- Toàn bộ đặt hàng chạy trong một transaction, khóa User và Product, kiểm tra lại tồn kho và giá; thất bại sẽ rollback. Mã giỏ chống tạo đơn trùng khi gửi lại yêu cầu.
- Mỗi tài khoản chỉ xem/sửa giỏ và lịch sử của mình. POST có token chống CSRF. Giỏ lưu database nên vẫn còn sau khi đăng xuất.
- Thay đổi trạng thái bằng SQL phục vụ bài kiểm tra, không tự động hoàn tồn kho khi hủy/hoàn.

## Kiểm tra thủ công

Đã chạy `mvn package`: 5 kiểm thử tự động đạt trên H2 riêng (giỏ hàng và quyền sở hữu, COD và chống gửi lặp, rollback tồn kho, dữ liệu nhận hàng/giá thay đổi, hai người mua đồng thời). Đã kiểm tra 4 JSP giỏ hàng, COD, lịch sử và chi tiết sản phẩm trả HTTP 200 trên Tomcat 11 với dữ liệu thử. Migration đã áp dụng thành công trên SQL Server `WebShop05` của máy hiện tại. Chưa chạy thao tác đặt đơn thật bằng trình duyệt trên dữ liệu người dùng.

- Thêm cùng sản phẩm hai lần: gộp và cộng số lượng; thử số lượng 0, âm, vượt stock và giá trị không phải số qua HTTP: phải bị từ chối.
- Cập nhật, xóa dòng cuối, mở thanh toán khi giỏ trống.
- Thông tin nhận hàng trống/sai số điện thoại; COD hợp lệ; bấm gửi lại: chỉ có một đơn và stock chỉ giảm một lần.
- Hai User mua sản phẩm tồn kho ít cùng lúc: không âm stock; một đơn thất bại phải giữ nguyên giỏ và tồn kho.
- Đổi giá/tồn kho sau khi thêm giỏ: checkout phải kiểm tra lại. Giá đổi thì cập nhật giỏ trước khi đặt.
- Đăng nhập User khác: không thấy đơn/giỏ của tài khoản trước. Khách chuyển đến login; Admin/Seller bị từ chối truy cập chức năng mua hàng.
- Thử đủ 8 trạng thái bằng SQL; bộ lọc Tất cả và từng trạng thái trả đúng đơn; giá đơn cũ không đổi khi sửa giá Product.
