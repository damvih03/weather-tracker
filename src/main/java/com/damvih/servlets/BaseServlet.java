package com.damvih.servlets;

import com.damvih.utils.ThymeleafUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

abstract public class BaseServlet extends HttpServlet {

    protected ITemplateEngine templateEngine;
    protected WebContext webContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        templateEngine = (ITemplateEngine) config
                .getServletContext()
                .getAttribute("templateEngine");

        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        webContext = (WebContext) ThymeleafUtil.buildWebContext(request, response, getServletContext());
        super.service(request, response);
    }

}
