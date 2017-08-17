package ru.otus.java_2017_04.golovnin.hw13.WebApp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    public static final String LOGIN_PARAMETER_NAME = "login";
    public static final String PASSWORD_PARAMETER_NAME = "pass";
    private static final String PATH_STORAGE_ATTRIBUTE_NAME = "redirectedPath";
    private static final String ERROR_LOGIN_PATH_ATTRIBUTE_NAME = "errorFormPath";
    private static final String AUTH_SERVICE_ATTRIBUTE_NAME = "authService";

    private static final int MAX_INACTIVE_TIME_SEC = 20;
    private String errorLoginPath;
    private AuthenticationService authService;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = this.getServletContext();
        errorLoginPath = (String) servletContext.getAttribute(ERROR_LOGIN_PATH_ATTRIBUTE_NAME);
        authService = (AuthenticationService) servletContext.getAttribute(AUTH_SERVICE_ATTRIBUTE_NAME);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN_PARAMETER_NAME);
        String pass = req.getParameter(PASSWORD_PARAMETER_NAME);
        HttpSession session = req.getSession();
        authService.login(session, login, pass);
        if(authService.isAuthorized(session)){
            session.setMaxInactiveInterval(MAX_INACTIVE_TIME_SEC);
            resp.sendRedirect((String) session.getAttribute(PATH_STORAGE_ATTRIBUTE_NAME));
        }
        else resp.sendRedirect(errorLoginPath);
    }

}
