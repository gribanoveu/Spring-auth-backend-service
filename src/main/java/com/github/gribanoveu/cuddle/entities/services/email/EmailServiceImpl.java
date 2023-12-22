package com.github.gribanoveu.cuddle.entities.services.email;

import com.github.gribanoveu.cuddle.dtos.data.SimpleEmailObject;
import com.github.gribanoveu.cuddle.exeptions.CredentialEx;
import com.github.gribanoveu.cuddle.dtos.enums.ResponseCode;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

/**
 * @author Evgeny Gribanov
 * @version 27.11.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    @Override
    public void sendEmail(String toEmail, String subject, String message) {
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        try {
            mailSender.send(simpleMailMessage);
        } catch (MailException e) {
            throw new CredentialEx(ResponseCode.EMAIL_NOT_SEND);
        }
    }

    @Override
    public void sendMail(SimpleEmailObject email) {
        try {
            var message = mailSender.createMimeMessage();
            var mimeMessageHelper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            var context = new Context();
            context.setVariables(email.context());
            var emailContent = templateEngine.process(email.templateLocation(), context);

            mimeMessageHelper.setTo(email.to());
            mimeMessageHelper.setSubject(email.subject());
            mimeMessageHelper.setFrom(email.from());
            mimeMessageHelper.setText(emailContent, true);

            mailSender.send(message);
        } catch (MailException | MessagingException e) {
            throw new CredentialEx(ResponseCode.EMAIL_NOT_SEND);
        }
    }
}
