package ru.otus.java_2017_04.golovnin.hw02;

import java.util.LinkedList;

public class ObjectScaleHelper {
    private LinkedList<ObjectFactory> tasks = new LinkedList<ObjectFactory>();
    private ObjectScale scale = new ObjectScale();

    public void addTask(ObjectFactory task)
    {
        tasks.offer(task);
    }

    public void operate()
    {
        ObjectFactory task;
        int tasksCount = tasks.size();
        for(int taskNum = 0; taskNum < tasksCount; taskNum++)
        {
            task = tasks.remove();
            System.out.print("Weight of " + task.getInstance().getClass().getName());
            System.out.println(" is "  + scale.weigh(task) + " bytes");
        }
    }
}
