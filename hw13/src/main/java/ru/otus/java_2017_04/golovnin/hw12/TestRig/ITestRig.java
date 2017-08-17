package ru.otus.java_2017_04.golovnin.hw12.TestRig;

import java.util.Map;

public interface ITestRig {
    void start();
    Map<String, Object> getParameters();
}
