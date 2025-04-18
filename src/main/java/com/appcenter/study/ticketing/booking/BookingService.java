package com.appcenter.study.ticketing.booking;

import com.appcenter.study.ticketing.booking.dto.response.CreateBookingResponse;
import com.appcenter.study.ticketing.common.response.ApiResponse;
import com.appcenter.study.ticketing.common.response.resEnum.SuccessCode;
import com.appcenter.study.ticketing.booking.dto.request.CreateBookingRequest;
import com.appcenter.study.ticketing.entity.Booking;
import com.appcenter.study.ticketing.entity.TicketStock;
import com.appcenter.study.ticketing.ticket.TicketRepository;
import com.appcenter.study.ticketing.entity.Ticket;
import com.appcenter.study.ticketing.ticketStock.TicketStockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookingService {
    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private final TicketStockRepository ticketStockRepository;

   // 티켓 예매 요청
    @Transactional
    public ApiResponse<?> createBooking(@RequestBody CreateBookingRequest request) {
        // 1. 티켓 조회
        Ticket ticket = ticketRepository.findById(request.getTicketId()).orElseThrow(() -> new RuntimeException("티켓 없음"));

        // 2. 티켓 수량 변경 (1개 감소)
        TicketStock ticketStock = ticketStockRepository.findByTicket(ticket);
        ticketStock.decreaseQuantity();

        // 3. 예약 정보 저장
        Booking booking = Booking.builder()
                .username(request.getUserName())
                .ticket(ticket)
                .build();
        bookingRepository.save(booking);

        // 4. DTO 변경
        CreateBookingResponse dto = CreateBookingResponse.builder()
                .bookingId(booking.getBookingId())
                .username(booking.getUsername())
                .ticket(booking.getTicket())
                .build();

        return ApiResponse.SUCCESS(SuccessCode.CREATE_BOOKING, dto);
    }
}
