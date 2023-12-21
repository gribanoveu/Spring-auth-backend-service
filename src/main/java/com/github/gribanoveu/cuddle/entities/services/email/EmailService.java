package com.github.gribanoveu.cuddle.entities.services.email;

import com.github.gribanoveu.cuddle.controllers.dtos.data.SimpleEmailObject;

/**
 * @author Evgeny Gribanov
 * @version 27.11.2023
 */
public interface EmailService {
    void sendEmail(String toEmail, String subject, String message);
    void sendMail(SimpleEmailObject email);
}
