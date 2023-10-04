package com.github.gribanoveu.auth.controllers.exeptions.entrypoint;

import com.github.gribanoveu.auth.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import com.github.gribanoveu.auth.entities.enums.StatusLevel;
import com.github.gribanoveu.auth.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Evgeny Gribanov
 * @version 06.09.2023
 */
@Component
@RequiredArgsConstructor
public class AccessDeniedEntryPoint implements AccessDeniedHandler {
    private final JsonUtils jsonUtils;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        var error = StatusResponse.create(ResponseCode.ACCESS_DENIED, StatusLevel.ERROR);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonUtils.convertDtoToJson(error));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
