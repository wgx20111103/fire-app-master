package io.renren.modules.app.utils;


import io.renren.common.utils.JsonUtil;
import org.jboss.logging.Logger;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailUtils {
    private static final Logger logger = Logger.getLogger(EmailUtils.class);
    // 发送邮件的方法
    public static void sendEmail(String to, String subject, String body)  {
        try {
        // 设置邮件服务器属性
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.qq.com"); // 替换为你的SMTP服务器地址
        properties.put("mail.smtp.port", "587"); // 替换为你的SMTP服务器端口

        // 创建会话
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1037729032@qq.com", "tixgidzsaxgzbeaf"); // 替换为你的邮箱和密码
            }
        });

        // 创建邮件消息
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("1037729032@qq.com")); // 替换为你的邮箱
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);
        // 发送邮件
        Transport.send(message);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 生成6位数验证码
        return String.valueOf(code);
    }
}