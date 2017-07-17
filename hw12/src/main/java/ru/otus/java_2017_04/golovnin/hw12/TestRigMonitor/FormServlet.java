package ru.otus.java_2017_04.golovnin.hw12.TestRigMonitor;


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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message;
        String retryParameter = req.getParameter(TestRigMonitor.RETRY_PARAMETER_NAME);
        if(retryParameter != null && retryParameter.equals("true")){
            message = TRY_AGAIN_MESSAGE;
        }
        else message = LOGIN_MESSAGE;

        Map<String, Object> pageData = new HashMap<>();
        pageData.put(MESSAGE_DATA_NAME, message);
        String page = TemplateProcessor.instance().getPage(PAGE_FILE, pageData);
        resp.getWriter().println(page);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
