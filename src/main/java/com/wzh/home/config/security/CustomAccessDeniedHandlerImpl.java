package com.wzh.home.config.security;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/18 17:11
 */
@Slf4j
@Component
public class CustomAccessDeniedHandlerImpl implements AccessDeniedHandler {
    /**
     * Handles an access denied failure.
     *
     * @param request
     *            that resulted in an <code>AccessDeniedException</code>
     * @param response
     *            so that the user agent can be advised of the failure
     * @param accessDeniedException
     *            that caused the invocation
     * @throws IOException
     *             in the event of an IOException
     * @throws ServletException
     *             in the event of a ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info(accessDeniedException.getMessage());
    }
}
