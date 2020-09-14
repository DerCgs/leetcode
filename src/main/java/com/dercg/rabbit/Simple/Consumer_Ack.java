package com.dercg.rabbit.Simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer_Ack {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("211.159.148.205");
        factory.setPassword("password");
        factory.setUsername("user");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("[*] Waiting for message , to exit press CTRL+C");
        channel.basicQos(1);

        DeliverCallback deliverCallback = ((consumerTag, message) -> {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received " + msg);
            try {
                doWork(msg);
            } catch (InterruptedException e) {
                System.out.println("[x] " + msg + " Done ");
            } finally {
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        });

        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {

        });
    }

    public static void doWork(String message) throws InterruptedException {
        Thread.sleep(10000);
        System.out.println(message + " complete");
    }
}
