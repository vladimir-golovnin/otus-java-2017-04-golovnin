package ru.otus.java_2017_04.golovnin.hw12;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet{
    private static final String pageFile = "admin.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = TemplateProcessor.instance().getPage(pageFile, generatePageData());
        resp.getWriter().println(page);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private Map<String,Object> generatePageData() {
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("hits", 24);
        pageData.put("misses", 56);
        return pageData;
    }

}
