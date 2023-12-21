package com.github.gribanoveu.cuddle.controllers.exeptions.entrypoint;

import com.github.gribanoveu.cuddle.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddle.entities.enums.StatusLevel;
import com.github.gribanoveu.cuddle.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Evgeny Gribanov
 * @version 06.09.2023
 */
@Component
@RequiredArgsConstructor
public class AuthErrorEntryPoint implements AuthenticationEntryPoint {
    private final JsonUtils jsonUtils;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        var error = StatusResponse.create(
                ResponseCode.UNAUTHORIZED, authException.getLocalizedMessage(), StatusLevel.ERROR);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonUtils.convertDtoToJson(error));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
