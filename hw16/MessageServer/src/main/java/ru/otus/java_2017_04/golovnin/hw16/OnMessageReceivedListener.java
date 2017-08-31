package ru.otus.java_2017_04.golovnin.hw16;


public interface OnMessageReceivedListener {
    void processMessage(String message, ClientChannel source);
}
