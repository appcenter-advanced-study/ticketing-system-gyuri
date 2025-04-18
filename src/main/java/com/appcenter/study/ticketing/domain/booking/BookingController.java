package com.appcenter.study.ticketing.domain.booking;

import com.appcenter.study.ticketing.domain.booking.dto.request.CreateBookingRequest;
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

    // 티켓 예매 요청
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody CreateBookingRequest request) {
        return ResponseEntity.ok().body(bookingService.createBooking(request));
    }
}
