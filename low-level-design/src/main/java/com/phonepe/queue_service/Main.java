package com.phonepe.queue_service;


import java.util.*;
import java.util.concurrent.*;


class Message {

    private final String messageId;
    private final String payload;
    private int retryCount;

    public Message(String payload) {
        this.messageId = UUID.randomUUID().toString();
        this.payload = payload;
        this.retryCount = 0;
    }

    public void incRetryCount() {
        this.retryCount++;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", payload='" + payload + '\'' +
                ", retryCount=" + retryCount +
                '}';
    }
}

class Topic {

    private final String topicId;

    private final String topicName;

    public Topic(String topicName) {
        this.topicId = UUID.randomUUID().toString();
        this.topicName = topicName;
    }

}

interface Subscriber {
    void consume(Message message);

    String getId();

    String getName();
}

class MockSubscriber implements Subscriber {

    private final String subscriberId;
    private final String subscriberName;

    public MockSubscriber(String subscriberName) {
        this.subscriberName = subscriberName;
        this.subscriberId = UUID.randomUUID().toString();
    }

    @Override
    public void consume(Message message) {
        System.out.println("Consumed following message by subscriber : " + this.subscriberName);
        System.out.println(message);
    }

    @Override
    public String getId() {
        return this.subscriberId;
    }

    @Override
    public String getName() {
        return this.subscriberName;
    }
}


class TopicHandler {

    private final Set<Subscriber> subscriberList = new CopyOnWriteArraySet<>();
    private final BlockingQueue<Message> blockingQueue = new LinkedBlockingQueue<>();
    ExecutorService executorService;

    public TopicHandler(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public TopicHandler() {
        this.executorService = Executors.newFixedThreadPool(5);
    }

    public void addSubscriber(Subscriber subscriber) {
        this.subscriberList.add(subscriber);
    }

    public void publishToSubscriber(Message message) {
        try {
            this.blockingQueue.put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void startProcessing() {
        Thread thread = new Thread(() -> {
            while(true) {
                try {
                    Message message = blockingQueue.take();
                    broadcast(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    private void broadcast(Message message) {
        for (Subscriber subscriber : subscriberList) {
            executorService.submit(() -> subscriber.consume(message));
        }
    }
}

class TopicAlreadyExistsException extends RuntimeException {
    public TopicAlreadyExistsException(String message) {
        super(message);
    }
}

class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(String message) {
        super(message);
    }
}

class QueueService {

    private final Map<String, TopicHandler> topicHandlerByName = new ConcurrentHashMap<>();

    public QueueService() {
    }

    public void createTopic(String topicName) {
        TopicHandler existingTopicHandler = topicHandlerByName.get(topicName);
        TopicHandler newHandler = new TopicHandler();
        topicHandlerByName.putIfAbsent(topicName, newHandler);
        if (existingTopicHandler == null) {
            System.out.println("Topic with name :" + topicName + " created.");
            newHandler.startProcessing();
        } else {
            throw new TopicAlreadyExistsException("Topic with name : " + topicName + " already exists.");
        }
    }

    public void publish(String topicName, Message message) {
        TopicHandler topicHandler = topicHandlerByName.get(topicName);
        if (topicHandler != null) {
            System.out.println("Message with content :" + message + " published.");
            topicHandler.publishToSubscriber(message);
        } else {
            throw new TopicNotFoundException("Topic with name :" + topicName + " was not found.");
        }
    }

    public void subscribe(String topicName, Subscriber subscriber) {
        TopicHandler topicHandler = topicHandlerByName.get(topicName);
        if (topicHandler != null) {
            System.out.println(subscriber.getName() + " subscribed to : " + topicName);
            topicHandler.addSubscriber(subscriber);
        } else {
            throw new TopicNotFoundException("Topic with name :" + topicName + " was not found.");
        }
    }
}

public class Main {

    public static void main(String[] args) {
        QueueService queueService = new QueueService();
        Subscriber sub1 = new MockSubscriber("Payment Service");
        Subscriber sub2 = new MockSubscriber("Ordering Service");
        String PAYMENT_ALERT_TOPIC = "PaymentAlert";
        String messagePayload = "{Test PaymentAlert message}";
        queueService.createTopic(PAYMENT_ALERT_TOPIC);
        queueService.subscribe(PAYMENT_ALERT_TOPIC, sub1);
        queueService.subscribe(PAYMENT_ALERT_TOPIC, sub2);
        Message message = new Message(messagePayload);
        queueService.publish(PAYMENT_ALERT_TOPIC, message);
    }
}
