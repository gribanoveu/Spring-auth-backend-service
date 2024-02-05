package com.github.gribanoveu.cuddle.exeptions.entrypoint;

import com.github.gribanoveu.cuddle.exeptions.responses.RestResponse;
import com.github.gribanoveu.cuddle.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.github.gribanoveu.cuddle.exeptions.errors.ServerMessage.SERVER_ERROR;
import static com.github.gribanoveu.cuddle.exeptions.errors.UserMessage.ACCOUNT_BANNED;
import static com.github.gribanoveu.cuddle.exeptions.errors.UserMessage.ACCOUNT_DISABLED;

/**
 * @author Evgeny Gribanov
 * @version 06.09.2023
 */
@Component
@RequiredArgsConstructor
public class ServerErrorEntryPoint implements AuthenticationEntryPoint {
    private final JsonUtils jsonUtils;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        RestResponse error;

        if (authException instanceof DisabledException) error = RestResponse.create(ACCOUNT_DISABLED);
        else if (authException instanceof LockedException) error = RestResponse.create(ACCOUNT_BANNED);
        else error = RestResponse.create(SERVER_ERROR);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonUtils.convertDtoToJson(error));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
