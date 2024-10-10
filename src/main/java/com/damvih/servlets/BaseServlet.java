package com.damvih.servlets;

import com.damvih.utils.ThymeleafUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.IWebContext;

import java.io.IOException;

abstract public class BaseServlet extends HttpServlet {

    protected ITemplateEngine templateEngine;
    protected IWebContext webContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        templateEngine = (ITemplateEngine) config
                .getServletContext()
                .getAttribute("templateEngine");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        webContext = ThymeleafUtil.buildWebContext(request, response, getServletContext());
        super.service(request, response);
    }

}
