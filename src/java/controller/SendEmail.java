package controller;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author Oanh Nguyen
 */
public class SendEmail {

    // Tài khoản email gửi OTP (cấu hình theo email bạn sở hữu)
    //response.setContentType("text/plain;charset=UTF-8");
    private static final String FROM_EMAIL = "Oanhntkce181400@fpt.edu.vn";
    private static final String PASSWORD = "ttel jtjt kouv ipsz"; // Mật khẩu ứng dụng (không phải pass tài khoản Gmail)

    // Phương thức gửi email
    public static boolean send(String toEmail, String subject, String messageText) {
        // Cấu hình các thuộc tính cho server SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP server
        props.put("mail.smtp.port", "587"); // TLS port
        props.put("mail.smtp.auth", "true"); // Xác thực
        props.put("mail.smtp.starttls.enable", "true"); // Kích hoạt TLS

        // Tạo phiên làm việc
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            // Tạo đối tượng tin nhắn
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL)); // Email người gửi
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Email người nhận
            message.setSubject(subject); // Tiêu đề email
            message.setText(messageText); // Nội dung email

            // Gửi email
            Transport.send(message);
            System.out.println("Email đã gửi thành công tới: " + toEmail);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace(); // In lỗi nếu có
            return false;
        }
    }
}
