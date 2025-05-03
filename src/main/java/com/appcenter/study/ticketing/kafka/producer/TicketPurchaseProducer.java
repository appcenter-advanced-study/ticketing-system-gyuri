package com.appcenter.study.ticketing.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TicketPurchaseProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendPurchaseRequest(String ticketId, String userId) {
        kafkaTemplate.send("ticket-purchase-topic", ticketId, userId);
    }
}
