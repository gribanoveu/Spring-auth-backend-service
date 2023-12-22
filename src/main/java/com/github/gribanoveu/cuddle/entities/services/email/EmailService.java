package com.github.gribanoveu.cuddle.entities.services.email;

import com.github.gribanoveu.cuddle.dtos.data.SimpleEmailObject;

/**
 * @author Evgeny Gribanov
 * @version 27.11.2023
 */
public interface EmailService {
    void sendMail(SimpleEmailObject email);
}
