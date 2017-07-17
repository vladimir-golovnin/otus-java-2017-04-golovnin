package ru.otus.java_2017_04.golovnin.hw12;

import ru.otus.java_2017_04.golovnin.hw12.TestRig.DbServiceTestRig;
import ru.otus.java_2017_04.golovnin.hw12.TestRigMonitor.TestRigMonitor;

public class App
{

    private static final int MONITOR_PORT = 8080;

    public static void main( String[] args ) {
        DbServiceTestRig testRig = new DbServiceTestRig();
        TestRigMonitor monitor = new TestRigMonitor(MONITOR_PORT, testRig);
        monitor.start();
        testRig.start();
    }


}
