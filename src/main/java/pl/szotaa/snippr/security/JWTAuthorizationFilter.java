package pl.szotaa.snippr.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static pl.szotaa.snippr.security.SecurityConstants.HEADER_STRING;
import static pl.szotaa.snippr.security.SecurityConstants.SECRET;
import static pl.szotaa.snippr.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if(header == null || !header.startsWith(TOKEN_PREFIX)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(HEADER_STRING);
        if(token != null){
            String user = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJwt(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            if(user != null){
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
