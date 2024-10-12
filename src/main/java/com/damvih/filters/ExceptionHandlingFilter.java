package com.damvih.filters;

import com.damvih.exceptions.InvalidPasswordException;
import com.damvih.exceptions.RegistrationException;
import com.damvih.exceptions.UserNotFoundException;
import com.damvih.utils.ThymeleafUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandlingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (UserNotFoundException | InvalidPasswordException exception) {
            response.sendRedirect("/sign-in");
        } catch (RegistrationException exception) {
            response.sendRedirect("/sign-up");
        } catch (Exception exception) {
            ServletContext servletContext = request.getServletContext();

            WebContext webContext = (WebContext) ThymeleafUtil.buildWebContext(request, response, servletContext);
            webContext.setVariable("error", exception.getMessage());

            ITemplateEngine templateEngine = (ITemplateEngine) servletContext.getAttribute("templateEngine");
            templateEngine.process("error", webContext, response.getWriter());
        }
    }

}
