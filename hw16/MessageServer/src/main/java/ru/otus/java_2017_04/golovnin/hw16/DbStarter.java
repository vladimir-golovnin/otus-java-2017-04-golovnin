package ru.otus.java_2017_04.golovnin.hw16;


import java.io.IOException;

public class DbStarter {
    private static final String DB_START_COMMAND = "java -jar ../DbServer/target/DbServer.jar";
    public static void start(){
        ProcessBuilder processBuilder = new ProcessBuilder(DB_START_COMMAND.split(" "));
        try {
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
