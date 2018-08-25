package uow.itpm.teamblue.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import uow.itpm.teamblue.model.JwtAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthenticationTokenFilter() {
        super(SecurityConstants.defaultFilterProcessUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String token = request.getHeader("Authorisation");
        if(token == null || !token.startsWith("Token ")){
            throw new RuntimeException("Authorisation token is missing");
        }

        token = token.substring(6);
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
        return getAuthenticationManager().authenticate(jwtAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
