package ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting;

import ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting.ObjectFactory;
import ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting.ObjectScales;

import java.util.LinkedList;
import java.util.Queue;

public class ObjectScaleQueue {
    private Queue<ObjectFactory> tasks = new LinkedList<>();
    private ObjectScales scale = new ObjectScales();

    public void addTask(ObjectFactory task)
    {
        tasks.offer(task);
    }

    public void operate()
    {
        ObjectFactory task;
        while( (task = tasks.poll()) != null)
        {
            System.out.print("Weight of " + task.getObjectName());
            System.out.println(" is "  + scale.weigh(task) + " bytes");
        }
    }
}
