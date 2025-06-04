package com.tin.notificationservice.event;

import com.tin.commonservice.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class EventConsumer {
    @Autowired
    private EmailService emailService;

    @RetryableTopic(
            attempts = "4", //retry 3 times, 1 time with DLQ
            backoff = @Backoff(delay = 1000, multiplier = 2),
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            include = {
                    RetriableException.class, RuntimeException.class
            }
    )
    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message){

        log.info("Received message " + message);

        throw  new RuntimeException("Error Test");
    }

    @DltHandler
    void processDltMessage(@Payload String message){
        log.info("DLT receive message " + message);
    }

    @KafkaListener(topics = "testEmail", containerFactory = "kafkaListenerContainerFactory")
    public void testEmail(String message){
        try {
            log.info("Received message " + message);

            String template = "<div>\n" +
                    "    <h1>Welcome, %s!</h1>\n" +
                    "    <p>Thank you for joining us. We're excited to have you on board.</p>\n" +
                    "    <p>Your username is: <strong>%s</strong></p>\n" +
                    "</div>";

            String filledTemplate = String.format(template, "Tin Vo", message);

            emailService.sendEmail(message, "Thanks for your message", filledTemplate, true, null);
        } catch(Exception e){
            throw e;
        }
    }

    @KafkaListener(topics = "emailTemplate", containerFactory = "kafkaListenerContainerFactory")
    public void emailTemplate(String message){

        log.info("Received message " + message);

        Map<String,Object> placeholders = new HashMap<>();
        placeholders.put("name","Tin Vo");

        emailService.sendEmailWithTemplate(message,"Welcome to Lib Management System","emailTemplate.ftl",placeholders,null);
    }
}
