package com.appcenter.study.ticketing.domain.booking;

import com.appcenter.study.ticketing.domain.booking.dto.request.CreateBookingRequest;
import com.appcenter.study.ticketing.kafka.producer.TicketPurchaseProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;
    private final TicketPurchaseProducer ticketPurchaseProducer;

    // 티켓 예매 요청
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody CreateBookingRequest request) {
        return ResponseEntity.ok().body(bookingService.createBooking(request));
    }

    // 티켓 예매 요청 (kafka)
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody CreateBookingRequest request) {
        // kafka 로 예매 요청 발행
        ticketPurchaseProducer.sendPurchaseRequest(String.valueOf(request.getTicketId()), request.getUserName());
        return ResponseEntity.ok("예매 요청 전송 완료");
    }
}
