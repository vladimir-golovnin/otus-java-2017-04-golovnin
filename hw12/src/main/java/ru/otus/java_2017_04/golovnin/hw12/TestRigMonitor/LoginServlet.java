package ru.otus.java_2017_04.golovnin.hw12.TestRigMonitor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    public static final String LOGIN_PARAMETER_NAME = "login";
    public static final String PASSWORD_PARAMETER_NAME = "pass";

    private static final int MAX_INACTIVE_TIME_SEC = 30;
    private IAuthenticationData authData;

    public LoginServlet(IAuthenticationData aData){
        authData = aData;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN_PARAMETER_NAME);
        String pass = req.getParameter(PASSWORD_PARAMETER_NAME);
        if(authData.checkValid(login, pass)){
            req.getSession().setAttribute(TestRigMonitor.AUTH_ATTRIBUTE_NAME, true);
            req.getSession().setMaxInactiveInterval(MAX_INACTIVE_TIME_SEC);
            resp.sendRedirect(TestRigMonitor.ADMIN_PAGE_PATH);
        }
        else resp.sendRedirect(TestRigMonitor.LOGIN_FORM_PATH
                + "?" + TestRigMonitor.RETRY_PARAMETER_NAME + "=true");
    }

}
