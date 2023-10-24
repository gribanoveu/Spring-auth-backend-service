package com.github.gribanoveu.cuddly.controllers.exeptions.entrypoint;

import com.github.gribanoveu.cuddly.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddly.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddly.entities.enums.StatusLevel;
import com.github.gribanoveu.cuddly.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.github.gribanoveu.cuddly.entities.enums.ResponseCode.*;

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
        StatusResponse error = null;

        if (authException instanceof DisabledException) {
            error = StatusResponse.create(ACCOUNT_DISABLED, ACCOUNT_DISABLED.getMessage(), StatusLevel.ERROR);

        } else if (authException instanceof LockedException) {
            error = StatusResponse.create(ACCOUNT_BANNED, ACCOUNT_BANNED.getMessage(), StatusLevel.ERROR);

        } else error = StatusResponse.create(ACCESS_DENIED, authException.getMessage(), StatusLevel.ERROR);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonUtils.convertDtoToJson(error));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
