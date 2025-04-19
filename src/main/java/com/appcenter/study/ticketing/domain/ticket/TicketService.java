package com.appcenter.study.ticketing.domain.ticket;

import com.appcenter.study.ticketing.common.response.ApiResponse;
import com.appcenter.study.ticketing.common.response.resEnum.SuccessCode;
import com.appcenter.study.ticketing.domain.ticket.dto.response.TicketListResponse;
import com.appcenter.study.ticketing.domain.ticketStock.TicketStock;
import com.appcenter.study.ticketing.domain.ticketStock.TicketStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketStockRepository ticketStockRepository;

    // 티켓 목록 전체 조회
    public ApiResponse<?> searchAllTickets() {
        // 1. 티켓 조회
        List<TicketStock> ticketStocks = ticketStockRepository.findAll();

        // 2. DTO 변경
        List<TicketListResponse> dto = new ArrayList<>();
        for (TicketStock ticketStock : ticketStocks) {
            dto.add(TicketListResponse.builder()
                    .ticketStockId(ticketStock.getTicketStockId())
                    .quantity(ticketStock.getQuantity())
                    .ticket(ticketStock.getTicket())
                    .build());
        }

        return ApiResponse.SUCCESS(SuccessCode.FOUND_IT, dto);
    }
}
