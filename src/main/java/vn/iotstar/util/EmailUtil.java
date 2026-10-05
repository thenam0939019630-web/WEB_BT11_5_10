package vn.iotstar.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public final class EmailUtil {
    private EmailUtil() { }
    private static final Properties LOCAL_SETTINGS = loadLocalSettings();
    private static Properties loadLocalSettings() {
        Properties properties = new Properties();
        try (var stream = EmailUtil.class.getResourceAsStream("/mail-local.properties")) {
            if (stream != null) properties.load(new java.io.InputStreamReader(stream, java.nio.charset.StandardCharsets.UTF_8));
        } catch (java.io.IOException e) {
            throw new IllegalStateException("Không đọc được cấu hình email cục bộ.", e);
        }
        return properties;
    }
    static String setting(String key, String fallback) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) value = System.getenv(key);
        if (value == null || value.isBlank()) value = LOCAL_SETTINGS.getProperty(key);
        return value == null || value.isBlank() ? fallback : value;
    }
    public static void sendOTP(String toEmail, String otp) {
        String username = setting("WEBSHOP_MAIL_USERNAME", "");
        String password = setting("WEBSHOP_MAIL_PASSWORD", "");
        if (username.isBlank() || password.isBlank()) {
            throw new IllegalStateException("Chưa cấu hình tài khoản gửi email. Hãy cấu hình WEBSHOP_MAIL_USERNAME và WEBSHOP_MAIL_PASSWORD cho Tomcat rồi khởi động lại.");
        }
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", setting("WEBSHOP_MAIL_HOST", "smtp.gmail.com"));
        props.setProperty("mail.smtp.port", setting("WEBSHOP_MAIL_PORT", "587"));
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.starttls.required", "true");
        props.setProperty("mail.smtp.ssl.checkserveridentity", "true");
        props.setProperty("mail.smtp.connectiontimeout", "10000");
        props.setProperty("mail.smtp.timeout", "10000");
        props.setProperty("mail.smtp.writetimeout", "10000");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, true));
            message.setSubject("OTP đăng ký WebShop", "UTF-8");
            message.setText("Mã OTP của bạn là: " + otp, "UTF-8");
            Transport.send(message);
        } catch (AuthenticationFailedException e) {
            throw new IllegalStateException("Máy chủ email từ chối đăng nhập. Hãy kiểm tra tài khoản và mật khẩu SMTP đã cấu hình.", e);
        } catch (MessagingException e) {
            throw new IllegalStateException("Không gửi được email OTP. Vui lòng kiểm tra cấu hình SMTP, kết nối mạng và thử lại.", e);
        }
    }
}
