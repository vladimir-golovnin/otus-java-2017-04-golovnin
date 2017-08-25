package ru.otus.java_2017_04.golovnin.hw15.Frontend;


public class WebsocketMessage {
    public static final int TYPE_ERROR = 0;
    public static final int TYPE_USER_DATA = 1;


    private int type;
    private Object content;

    public WebsocketMessage(int type, Object content){
        this.type = type;
        this.content = content;
    }
}
