package com.appcenter.study.ticketing.domain.booking;

import com.appcenter.study.ticketing.domain.booking.dto.response.CreateBookingResponse;
import com.appcenter.study.ticketing.common.response.ApiResponse;
import com.appcenter.study.ticketing.common.response.resEnum.SuccessCode;
import com.appcenter.study.ticketing.domain.booking.dto.request.CreateBookingRequest;
import com.appcenter.study.ticketing.domain.ticketStock.TicketStock;
import com.appcenter.study.ticketing.domain.ticket.TicketRepository;
import com.appcenter.study.ticketing.domain.ticket.Ticket;
import com.appcenter.study.ticketing.domain.ticketStock.TicketStockRepository;
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
//        TicketStock ticketStock = ticketStockRepository.findByTicket(ticket);
        // 비관적 락
        TicketStock ticketStock = ticketStockRepository.findByTicketIdForUpdate(ticket.getTicketId())
                .orElseThrow(() -> new RuntimeException("티켓 재고 없음"));
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

    // 티켓 예매 요청 (kafka)
    @Transactional
    public void purchaseTicket(Long ticketId, String username) {
        // 1. 티켓 조회
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("티켓 없음"));

        // 2. 티켓 재고 조회 + 감소
        TicketStock ticketStock = ticketStockRepository.findByTicketIdForUpdate(ticket.getTicketId())
                .orElseThrow(() -> new RuntimeException("티켓 재고 없음"));

        ticketStock.decreaseQuantity();

        // 3. 예약 저장
        Booking booking = Booking.builder()
                .username(username)
                .ticket(ticket)
                .build();
        bookingRepository.save(booking);

        log.info("[Kafka] 예매 성공: ticketId={}, username={}", ticketId, username);
    }
}
