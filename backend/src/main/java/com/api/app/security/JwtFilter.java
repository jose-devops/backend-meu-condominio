package com.api.app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Ignora as rotas públicas (login e cadastro)
        if (request.getRequestURI().equals("/usuario/login") || request.getRequestURI().equals("/usuario/cadastrar")) {
            filterChain.doFilter(request, response);  // Permite continuar sem verificar o token
            return;
        }

        String token = getTokenFromRequest(request);  // Obtém o token da requisição

        if (token != null && jwtUtil.validateToken(token, "proprietario")) {  // Valida o token
            String role = jwtUtil.extractRole(token);  // Extrai o papel do token

            if ("proprietario".equals(role)) {  // Verifica se o papel é 'proprietario'
                filterChain.doFilter(request, response);  // Permite o acesso
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Acesso negado: Não é um proprietário");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido ou não fornecido");
        }
    }

    // Método para extrair o token da requisição
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Remove "Bearer " do começo do token
        }
        return null;
    }
}

