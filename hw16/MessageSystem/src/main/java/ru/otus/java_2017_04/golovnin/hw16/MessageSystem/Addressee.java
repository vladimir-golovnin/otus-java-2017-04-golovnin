package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

public class Addressee {
    private static final int MESSAGE_QUEUE_CAPACITY = 32;
    private static final long QUEUE_SERVICE_DEFAULT_PERIOD_MILLIS = 10;

    private MessageSystem messageSystem;
    private Address address;
    private Object client;
    private long queueServicePeriodMillis = QUEUE_SERVICE_DEFAULT_PERIOD_MILLIS;

    private static final byte MIN_LOAD = 0;
    private static final byte MAX_LOAD = 100;
    private byte workload = MIN_LOAD;

    private boolean livingIsPermitted;

    private Queue<Command> messageQueue = new ArrayBlockingQueue<>(MESSAGE_QUEUE_CAPACITY);

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

    public final void receiveMessage(Command message){
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

                if(queueServicePeriodMillis > processDurationMillis) {
                    workload = (byte)((processDurationMillis * MAX_LOAD) / queueServicePeriodMillis);
                } else workload = MAX_LOAD;

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

    public byte getWorkload() {
        return workload;
    }

    private void processMessage(Command message){
        Consumer<Addressee> consumer = message.execute(client);
        if(consumer != null) consumer.accept(this);
    }
}
