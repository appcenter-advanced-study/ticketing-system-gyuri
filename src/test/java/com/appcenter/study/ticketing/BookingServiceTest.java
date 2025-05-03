package com.appcenter.study.ticketing;

import com.appcenter.study.ticketing.domain.booking.BookingService;
import com.appcenter.study.ticketing.domain.booking.dto.request.CreateBookingRequest;
import com.appcenter.study.ticketing.kafka.consumer.TicketPurchaseConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@EnableKafka
public class BookingServiceTest {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // 동시에 요청할 사용자 수 (예: 50명)
    private final int THREAD_COUNT = 50;
    private final String topic = "ticket-purchase-topic";
    private final Long ticketId = 1L;

    @Test
    public void requests_100_AtTheSameTime() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10); // 동시에 실행할 스레드 수 제한
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT); // 50개의 요청이 모두 끝날 때까지 대기하도록 수 제한

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    CreateBookingRequest request = CreateBookingRequest.builder()
                            .ticketId(1L)
                            .userName("test")
                            .build();
                    bookingService.createBooking(request);
                } catch (Exception e) {
                    System.out.println("예매 실패 : " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 요청이 끝날 때까지 대기
        System.out.println("예매 시뮬레이션 완료");
    }

    /**
     * Kafka를 통해 예매 요청 50건 전송 테스트
     */
    @Test
    public void requests_50_through_Kafka() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        TicketPurchaseConsumer.latch = latch;

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int userIndex = i;
            executorService.submit(() -> {
                try {
                    String username = "user" + userIndex;
                    kafkaTemplate.send(topic, ticketId.toString(), username);
                } catch (Exception e) {
                    System.out.println("[Kafka] 전송 실패 : " + e.getMessage());
                }
            });
        }

        boolean completed = latch.await(10, java.util.concurrent.TimeUnit.SECONDS);
        if (completed) {
            System.out.println("[Kafka] 예매 시뮬레이션 성공");
        } else {
            System.out.println("[Kafka] 예매 시뮬레이션 실패: 타임아웃");
        }
    }
}
