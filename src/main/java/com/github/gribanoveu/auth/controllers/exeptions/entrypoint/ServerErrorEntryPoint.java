package com.github.gribanoveu.auth.controllers.exeptions.entrypoint;

import com.github.gribanoveu.auth.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import com.github.gribanoveu.auth.entities.enums.StatusLevel;
import com.github.gribanoveu.auth.utils.JsonUtils;
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
public class ServerErrorEntryPoint implements AuthenticationEntryPoint {
    private final JsonUtils jsonUtils;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        var error = StatusResponse.create(
                ResponseCode.ACCESS_DENIED, authException.getMessage(), StatusLevel.ERROR);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonUtils.convertDtoToJson(error));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
