# WebShop - Lập Trình Web

## Công nghệ sử dụng

- Java Servlet
- JSP
- JPA Hibernate
- SQL Server
- Maven
- Sitemesh 3
- JSTL


## Mô tả

Website bán hàng được xây dựng theo mô hình 3 tầng:

- Presentation Layer (MVC)
- Business Layer
- Data Access Layer


Chức năng chính:

- Đăng ký tài khoản OTP Email
- Đăng nhập / Đăng xuất Session
- Phân quyền User / Seller / Admin
- Hiển thị danh sách sản phẩm
- Xem chi tiết sản phẩm
- CRUD Category
- CRUD Product
- Phân trang


## Cấu hình Database

Database:
File tạo database:
data.sql

Cấu hình kết nối trong:
persistence.xml

Ứng dụng yêu cầu JDK 25+, Maven và Tomcat 11. Cấu hình SQL Server mặc định trong
`src/main/resources/META-INF/persistence.xml` là `localhost:1433`, database
`WebShop05`, user `sa`, password `123456`.

Để gửi OTP Gmail, cấu hình hai biến môi trường trước khi chạy Tomcat:
`WEBSHOP_MAIL_USERNAME` và `WEBSHOP_MAIL_PASSWORD` (Gmail App Password).

Thông tin test:

Username: admin
Password: 123456
Role: Admin
