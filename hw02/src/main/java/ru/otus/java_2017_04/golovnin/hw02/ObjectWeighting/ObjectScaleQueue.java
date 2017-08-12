package ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting;


import java.util.LinkedList;
import java.util.Queue;

public class ObjectScaleQueue {
    private Queue<ScaleTask> tasks = new LinkedList<>();
    private ObjectScales scale = new ObjectScales();

    private class ScaleTask{
        private final String name;
        private final ObjectFactory factory;

        public ScaleTask(String name, ObjectFactory factory){
            this.name = name;
            this.factory = factory;
        }

        public ObjectFactory getFactory() {
            return factory;
        }

        public String getName() {
            return name;
        }
    }

    public void addTask(ObjectFactory factory, String objectName)
    {
        tasks.offer(new ScaleTask(objectName, factory));
    }

    public void operate()
    {
        ScaleTask task;
        while( (task = tasks.poll()) != null)
        {
            System.out.print("Weight of " + task.getName());
            System.out.println(" is "  + scale.weigh(task.getFactory()) + " bytes");
        }
    }
}
