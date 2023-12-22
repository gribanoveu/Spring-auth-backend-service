package com.github.gribanoveu.cuddle.utils.emails;

import com.github.gribanoveu.cuddle.constants.EmailMessages;
import com.github.gribanoveu.cuddle.dtos.data.SimpleEmailObject;

import java.time.Duration;
import java.util.Map;

/**
 * @author Evgeny Gribanov
 * @version 28.11.2023
 */
public abstract class EmailTemplates {
    public static SimpleEmailObject generateOtpEmail(String sendToEmail, String otpCode, Duration otpCodeLifeTime) {
        return new SimpleEmailObject(
                EmailMessages.sendFrom,
                sendToEmail,
                EmailMessages.restorePasswordSubject,
                EmailMessages.restorePasswordTemplate,
                Map.of(
                        "otpCode", otpCode,
                        "otpCodeLifetime", otpCodeLifeTime.toMinutes(),
                        "email", sendToEmail
                )
        );
    }

    public static SimpleEmailObject simpleEmail(String sendToEmail, String subject, String templateName) {
        return new SimpleEmailObject(EmailMessages.sendFrom,sendToEmail,subject,templateName,
                Map.of("email", sendToEmail)
        );
    }

    public static SimpleEmailObject emailChanged(String oldEmail, String newEmail) {
        return new SimpleEmailObject(EmailMessages.sendFrom, oldEmail, EmailMessages.changeEmailSubject,
                EmailMessages.changeEmailTemplate,
                Map.of(
                        "oldEmail", oldEmail,
                        "newEmail", newEmail
                )
        );
    }
}
