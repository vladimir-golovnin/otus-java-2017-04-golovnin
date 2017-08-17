package ru.otus.java_2017_04.golovnin.hw13.WebApp;

import org.springframework.context.ApplicationContext;
import ru.otus.java_2017_04.golovnin.hw11.Cache.CacheEngine;
import ru.otus.java_2017_04.golovnin.hw11.DbService.DbService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AdminServlet extends HttpServlet{
    private static final String pageFile = "admin.html";
    private static final String AUTH_ATTRIBUTE_NAME = "authorized";
    private static final String PATH_STORAGE_ATTRIBUTE_NAME = "redirectedPath";
    private static final String LOGIN_FORM_PATH_ATTRIBUTE_NAME = "loginFormPath";
    private static final String APP_CONTEXT_ATTRIBUTE_NAME = "appContext";
    private static final String DBSERVICE_BEAN_NAME = "DbService";
    private static final String CACHE_BEAN_NAME = "DbServiceCache";
    private static final String TEMPLATE_PROCESSOR_BEAN_NAME = "TemplateProcessor";
    private static final String AUTH_SERVICE_ATTRIBUTE_NAME = "authService";

    private String loginPath;
    private DbService dbService;
    private CacheEngine cache;
    private TemplateProcessor templateProcessor;
    private AuthenticationService authService;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = this.getServletContext();
        loginPath = (String) servletContext.getAttribute(LOGIN_FORM_PATH_ATTRIBUTE_NAME);
        ApplicationContext appContext = (ApplicationContext) servletContext.getAttribute(APP_CONTEXT_ATTRIBUTE_NAME);
        dbService = (DbService) appContext.getBean(DBSERVICE_BEAN_NAME);
        cache = (CacheEngine) appContext.getBean(CACHE_BEAN_NAME);
        templateProcessor = (TemplateProcessor) appContext.getBean(TEMPLATE_PROCESSOR_BEAN_NAME);
        authService = (AuthenticationService) servletContext.getAttribute(AUTH_SERVICE_ATTRIBUTE_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(authService.isAuthorized(req.getSession())){
            String page = templateProcessor.getPage(pageFile, generatePageData());
            resp.getWriter().println(page);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/html; charset=UTF-8");
        }
        else{
            req.getSession().setAttribute(PATH_STORAGE_ATTRIBUTE_NAME, req.getContextPath());
            resp.sendRedirect(loginPath);
        }

    }

    private Map<String,Object> generatePageData() {
        Map<String, Object> parameters = new TreeMap<>();
        parameters.put("Access count", dbService.getAccessCount());
        parameters.put("Cache hit count", cache.getHitCount());
        parameters.put("Cache miss count", cache.getMissCount());
        parameters.put("Cache fill factor", cache.getFillFactor());
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("parameters", parameters);
        return pageData;
    }

}
