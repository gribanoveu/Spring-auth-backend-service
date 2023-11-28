package com.github.gribanoveu.cuddly.entities.services.email;

import com.github.gribanoveu.cuddly.controllers.dtos.data.AbstractEmailContext;
import jakarta.mail.MessagingException;

/**
 * @author Evgeny Gribanov
 * @version 27.11.2023
 */
public interface EmailService {
    void sendEmail(String toEmail, String subject, String message);
    void sendMail(AbstractEmailContext email);
}
