package ru.otus.java_2017_04.golovnin.hw15.Frontend;


import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FormServlet extends HttpServlet{

    private static final String PAGE_FILE = "form.html";
    private static final String MESSAGE_DATA_NAME = "message";
    private static final String LOGIN_MESSAGE = "Please login";
    private static final String TRY_AGAIN_MESSAGE = "Try again";
    private static final String TEMPLATE_PROCESSOR_BEAN_NAME = "TemplateProcessor";
    private static final String APP_CONTEXT_ATTRIBUTE_NAME = "appContext";
    private static final String AUTH_SERVICE_ATTRIBUTE_NAME = "authService";

    private TemplateProcessor templateProcessor;
    private AuthenticationService authService;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = this.getServletContext();
        ApplicationContext appContext = (ApplicationContext) servletContext.getAttribute(APP_CONTEXT_ATTRIBUTE_NAME);
        templateProcessor = (TemplateProcessor) appContext.getBean(TEMPLATE_PROCESSOR_BEAN_NAME);
        authService = (AuthenticationService) servletContext.getAttribute(AUTH_SERVICE_ATTRIBUTE_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message;

        if(authService.getLoginTriesCount(req.getSession()) > 0){
            message = TRY_AGAIN_MESSAGE;
        }
        else message = LOGIN_MESSAGE;

        Map<String, Object> pageData = new HashMap<>();
        pageData.put(MESSAGE_DATA_NAME, message);
        String page = templateProcessor.getPage(PAGE_FILE, pageData);
        resp.getWriter().println(page);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
