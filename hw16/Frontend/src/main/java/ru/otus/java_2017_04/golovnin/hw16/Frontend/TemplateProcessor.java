package ru.otus.java_2017_04.golovnin.hw16.Frontend;


import java.io.IOException;
import java.util.Map;

public interface TemplateProcessor {
    String getPage(String filename, Map<String, Object> data) throws IOException;
}
