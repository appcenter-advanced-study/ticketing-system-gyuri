package com.appcenter.study.ticketing;

import com.appcenter.study.ticketing.domain.booking.BookingService;
import com.appcenter.study.ticketing.domain.booking.dto.request.CreateBookingRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class BookingServiceTest {
    @Autowired
    private BookingService bookingService;

    // 동시에 요청할 사용자 수 (예: 50명)
    private final int THREAD_COUNT = 50;

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
}
