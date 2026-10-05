# Cấu hình gửi OTP

Cấu hình từ bài cũ đã được khôi phục vào `src/main/resources/mail-local.properties` trên máy hiện tại. File này được Git bỏ qua; ứng dụng dùng làm cấu hình dự phòng khi chưa đặt system properties/biến môi trường. Clean/Publish project và khởi động lại Tomcat để nạp cấu hình. Nếu mật khẩu cũ đã bị thu hồi, cập nhật mật khẩu trong file này.

File cấu hình cục bộ được đóng gói trong WAR khi build: không chia sẻ WAR chứa thông tin thật. Khi triển khai nơi khác, bỏ file cục bộ khỏi bản build và dùng biến môi trường bên dưới.

Lỗi thiếu `WEBSHOP_MAIL_USERNAME` / `WEBSHOP_MAIL_PASSWORD` nghĩa là Tomcat chưa có tài khoản gửi email. Migration SQL không giải quyết lỗi này.

## Eclipse

1. Dừng Tomcat trong tab **Servers**.
2. Nhấp đúp Tomcat → **Open launch configuration** → **Environment**.
3. Thêm `WEBSHOP_MAIL_USERNAME`: địa chỉ email của tài khoản gửi.
4. Thêm `WEBSHOP_MAIL_PASSWORD`: mật khẩu SMTP/mật khẩu ứng dụng do nhà cung cấp email cấp.
5. Mặc định là `smtp.gmail.com`, cổng `587`, STARTTLS. Nếu dùng SMTP khác, thêm `WEBSHOP_MAIL_HOST` và `WEBSHOP_MAIL_PORT` tương ứng với máy chủ hỗ trợ STARTTLS.
6. Chọn **Apply**, khởi động lại Tomcat và đăng ký lại.

Cũng hỗ trợ JVM system properties cùng tên (`-DWEBSHOP_MAIL_USERNAME=...`), ưu tiên hơn biến môi trường. Không đưa mật khẩu thật vào source code, Git hoặc chat.

## Tomcat ngoài Eclipse

Cấu hình các biến môi trường trên cho tiến trình khởi động Tomcat rồi khởi động lại Tomcat. Nếu chạy như Windows Service, cấu hình system properties trong Java Options của service rồi restart service.

## Kiểm tra

Đăng ký bằng username/email chưa tồn tại, kiểm tra hộp thư người nhận (kể cả thư rác). Chỉ sau khi gửi email thành công, ứng dụng mới chuyển sang nhập OTP. Nhập đúng mã để tạo tài khoản User rồi đăng nhập.

Nếu SMTP từ chối đăng nhập, kiểm tra thông tin tài khoản gửi. Nếu lỗi kết nối, kiểm tra host, port, mạng và log Tomcat. Project không tự bỏ xác minh email khi thiếu cấu hình SMTP.
