package ru.otus.java_2017_04.golovnin.hw12.TestRigMonitor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet{
    private static final String pageFile = "admin.html";
    private final TestRigMonitor monitor;

    public AdminServlet(TestRigMonitor monitor){
        this.monitor = monitor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean authenticated = (Boolean) req.getSession().getAttribute(TestRigMonitor.AUTH_ATTRIBUTE_NAME);
        if(authenticated != null && authenticated ) {
            String page = TemplateProcessor.instance().getPage(pageFile, generatePageData());
            resp.getWriter().println(page);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/html; charset=UTF-8");
        }
        else resp.sendRedirect(TestRigMonitor.LOGIN_FORM_PATH);

    }

    private Map<String,Object> generatePageData() {
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("parameters", monitor.getParameters());
        return pageData;
    }

}
