package com.germinai.back.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@germinai.com}")
    private String fromEmail;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${app.email.mode:mock}")
    private String emailMode;

    public void sendPasswordResetEmail(String toEmail, String token) {
        String resetLink = frontendUrl + "/reset-password?token=" + token;

        if ("mock".equalsIgnoreCase(emailMode)) {
            sendMockEmail(toEmail, token, resetLink);
        } else {
            sendRealEmail(toEmail, token, resetLink);
        }
    }

    private void sendMockEmail(String toEmail, String token, String resetLink) {
        logger.info("╔════════════════════════════════════════════════════╗");
        logger.info("║  CÓDIGO DE RECUPERAÇÃO DE SENHA - GERMINAI        ║");
        logger.info("╠════════════════════════════════════════════════════╣");
        logger.info("║  Para: {}                            ", toEmail);
        logger.info("║                                                    ║");
        logger.info("║  SEU CÓDIGO DE VERIFICAÇÃO:                        ║");
        logger.info("║                                                    ║");
        logger.info("║              ┌──────────┐                          ║");
        logger.info("║              │  {}  │                          ", token);
        logger.info("║              └──────────┘                          ║");
        logger.info("║                                                    ║");
        logger.info("║  Digite este código na página de recuperação      ║");
        logger.info("║  O código expira em 1 hora                        ║");
        logger.info("╚════════════════════════════════════════════════════╝");
    }

    private void sendRealEmail(String toEmail, String token, String resetLink) {
        try {
            String subject = "Germinai - Redefinição de Senha";
            String htmlContent = buildPasswordResetEmailTemplate(resetLink);

            sendHtmlEmail(toEmail, subject, htmlContent);

            logger.info("Email de redefinição de senha enviado para: {}", toEmail);
        } catch (Exception e) {
            logger.error("Erro ao enviar email de redefinição de senha para: {}", toEmail, e);
            throw new RuntimeException("Falha ao enviar email de redefinição de senha", e);
        }
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    private String buildPasswordResetEmailTemplate(String resetLink) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; border-radius: 5px 5px 0 0; }
                    .content { background-color: #f9f9f9; padding: 30px; border-radius: 0 0 5px 5px; }
                    .button { display: inline-block; padding: 12px 30px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px; margin: 20px 0; }
                    .footer { text-align: center; margin-top: 20px; font-size: 12px; color: #666; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Germinai</h1>
                    </div>
                    <div class="content">
                        <h2>Redefinição de Senha</h2>
                        <p>Olá,</p>
                        <p>Recebemos uma solicitação para redefinir a senha da sua conta Germinai.</p>
                        <p>Para redefinir sua senha, clique no botão abaixo:</p>
                        <p style="text-align: center;">
                            <a href="%s" class="button">Redefinir Senha</a>
                        </p>
                        <p>Ou copie e cole o seguinte link no seu navegador:</p>
                        <p style="word-break: break-all; background-color: #f0f0f0; padding: 10px; border-radius: 3px;">%s</p>
                        <p><strong>Este link expirará em 1 hora.</strong></p>
                        <p>Se você não solicitou a redefinição de senha, ignore este e-mail. Sua senha permanecerá inalterada.</p>
                        <p>Atenciosamente,<br>Equipe Germinai</p>
                    </div>
                    <div class="footer">
                        <p>Este é um e-mail automático. Por favor, não responda.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(resetLink, resetLink);
    }
}
