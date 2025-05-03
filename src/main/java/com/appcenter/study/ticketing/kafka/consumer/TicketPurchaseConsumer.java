package com.appcenter.study.ticketing.kafka.consumer;

import com.appcenter.study.ticketing.domain.booking.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketPurchaseConsumer {
    private final BookingService bookingService;

    // 테스트 환경용 CountDownLatch
    public static CountDownLatch latch;

    @KafkaListener(topics = "ticket-purchase-topic", groupId = "ticket-consumer-group")
    public void consume(@Payload String username,
                        @Header(KafkaHeaders.RECEIVED_KEY) String ticketId) {
        log.info("[Kafka] 메시지 수신: ticketId={}, username={}", ticketId, username);

        try {
            bookingService.purchaseTicket(Long.valueOf(ticketId), username);
        } catch (Exception e) {
            log.error("[Kafka] 예매 처리 중 오류 발생: {}", e.getMessage(), e);
        } finally {
            if (latch != null) {
                latch.countDown();
            }
        }
    }
}
