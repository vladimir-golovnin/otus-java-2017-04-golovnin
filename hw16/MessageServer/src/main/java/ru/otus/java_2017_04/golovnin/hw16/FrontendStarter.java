package ru.otus.java_2017_04.golovnin.hw16;


import java.io.IOException;

public class FrontendStarter {

    public static void start(int port){
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "java", "-jar", "../Frontend/target/Frontend.jar",
                    Integer.toString(port));
            try {
                processBuilder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
