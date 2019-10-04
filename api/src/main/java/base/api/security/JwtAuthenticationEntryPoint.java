package base.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger authLogger =
      LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    authLogger.error(
        "Responding with unauthorized error. Message - {}", authException.getMessage());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
  }
}
