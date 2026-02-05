package com.portfolio.backend.security;

import com.portfolio.backend.entity.User;
import com.portfolio.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    
    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Authentication authentication
    ) throws IOException {

        if (!(authentication.getPrincipal() instanceof OAuth2User oAuth2User)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid OAuth2 principal");
            return;
        }

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String photo = oAuth2User.getAttribute("photo");

        if (email == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email not found in OAuth2 response");
            return;
        }

        User user = userService.findOrCreateUser(email, name, photo);

        String jwt = jwtTokenProvider.generateToken(email);
        response.sendRedirect(frontendUrl + "/oauth2/success?token=" + jwt);
    }
}
