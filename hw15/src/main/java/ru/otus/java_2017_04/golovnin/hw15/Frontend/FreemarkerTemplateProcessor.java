package ru.otus.java_2017_04.golovnin.hw15.Frontend;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class FreemarkerTemplateProcessor implements TemplateProcessor {
    private final Configuration configuration;

    FreemarkerTemplateProcessor(String templatesDir) {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(), templatesDir);
    }

    public String getPage(String filename, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter();) {
            Template template = configuration.getTemplate(filename);
            template.process(data, stream);
            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
