package controller;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Simple authentication filter that redirects unauthenticated users to login.html.
 * It allows access to public resources: login, cadastrar, login.html, scripts, css, and any resources under /WEB-INF/lib if needed.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no-op
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Debug: print path and session info
        try {
            HttpSession debugSession = req.getSession(false);
            String debugUser = (debugSession != null) ? (String) debugSession.getAttribute("usuarioLogado") : null;
            System.out.println("[AuthFilter] path=" + path + " | usuarioLogado(session)=" + debugUser);
        } catch (Exception e) {
            System.out.println("[AuthFilter] debug error: " + e.getMessage());
        }

        // Allow public resources
        if (path.startsWith("/login.html") || path.startsWith("/cadastrar.html") || path.startsWith("/cadastrar") || path.startsWith("/logar")
                || path.startsWith("/scripts/") || path.startsWith("/css/") || path.startsWith("/webjars/")
                || path.startsWith("/META-INF/") || path.startsWith("/WEB-INF/") || path.startsWith("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("usuarioLogado") != null;

        // If not logged in and trying to access protected resources, redirect to login
        if (!loggedIn) {
            // If request is for root or index.html, redirect to login page
            resp.sendRedirect(req.getContextPath() + "/login.html");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // no-op
    }
}