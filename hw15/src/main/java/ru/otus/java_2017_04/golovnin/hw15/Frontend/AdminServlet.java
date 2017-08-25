package ru.otus.java_2017_04.golovnin.hw15.Frontend;

import org.springframework.context.ApplicationContext;
import ru.otus.java_2017_04.golovnin.hw15.Cache.CacheEngine;
import ru.otus.java_2017_04.golovnin.hw15.DbService.DbService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AdminServlet extends HttpServlet{
    private static final String pageFile = "/admin.html";
    private static final String PATH_STORAGE_ATTRIBUTE_NAME = "redirectedPath";
    private static final String LOGIN_FORM_PATH_ATTRIBUTE_NAME = "loginFormPath";
    private static final String APP_CONTEXT_ATTRIBUTE_NAME = "appContext";
    private static final String AUTH_SERVICE_ATTRIBUTE_NAME = "authService";

    private String loginPath;
    private AuthenticationService authService;
    private String page;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = this.getServletContext();
        loginPath = (String) servletContext.getAttribute(LOGIN_FORM_PATH_ATTRIBUTE_NAME);
        ApplicationContext appContext = (ApplicationContext) servletContext.getAttribute(APP_CONTEXT_ATTRIBUTE_NAME);
        authService = (AuthenticationService) servletContext.getAttribute(AUTH_SERVICE_ATTRIBUTE_NAME);
        page = "empty";

        InputStream is = servletContext.getResourceAsStream(pageFile);
        if(is != null) page = "stream";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(authService.isAuthorized(session)){
            resp.getWriter().println(page);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/html; charset=UTF-8");
        }
        else{
            session.setAttribute(PATH_STORAGE_ATTRIBUTE_NAME, req.getContextPath());
            resp.sendRedirect(loginPath);
        }

    }

}