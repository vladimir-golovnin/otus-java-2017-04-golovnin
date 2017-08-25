package ru.otus.java_2017_04.golovnin.hw15.MessageSystem;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Addressee {
    private static final int MESSAGE_QUEUE_CAPACITY = 32;
    private static final long QUEUE_SERVICE_DEFAULT_PERIOD_MILLIS = 10;



    private MessageSystem messageSystem;
    private Address address;
    private Object client;
    private long queueServicePeriodMillis = QUEUE_SERVICE_DEFAULT_PERIOD_MILLIS;
    private float workload = 0;
    private boolean livingIsPermitted;

    private Queue<Message> messageQueue = new ArrayBlockingQueue<>(MESSAGE_QUEUE_CAPACITY);

    public Addressee(MessageSystem messageSystem, Address address, Object client){
        this.messageSystem = messageSystem;
        this.address = address;
        this.client = client;
    }

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }
    public final void setQueueServicePeriod(long queueServicePeriodMillis) {
        this.queueServicePeriodMillis = queueServicePeriodMillis;
    }

    public final void receiveMessage(Message message){
        messageQueue.offer(message);
    }

    public final void live(){
        livingIsPermitted = true;
        new Thread(()->{
            while (true) {
                long processStartTime = System.currentTimeMillis();
                while (!messageQueue.isEmpty()) {
                    processMessage(messageQueue.poll());
                }
                long processEndTime = System.currentTimeMillis();
                long processDurationMillis = processEndTime - processStartTime;
                workload = (float) processDurationMillis / queueServicePeriodMillis;
                if(!livingIsPermitted) break;
                long spareTime = queueServicePeriodMillis - processDurationMillis;
                if(spareTime > 0) {
                    try {
                        Thread.sleep(spareTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public final void die(){
        livingIsPermitted = false;
    }

    public float getWorkload() {
        return workload;
    }

    private void processMessage(Message message){
        message.execute(client).accept(this);
    }
}
