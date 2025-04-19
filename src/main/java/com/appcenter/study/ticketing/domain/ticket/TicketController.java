package com.appcenter.study.ticketing.domain.ticket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    // 티켓 목록 전체 조회
    @GetMapping
    public ResponseEntity<?> searchAllTickets() {
        return ResponseEntity.ok(ticketService.searchAllTickets());
    }
}
