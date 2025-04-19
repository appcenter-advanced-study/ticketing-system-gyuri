package com.appcenter.study.ticketing.domain.ticket.dto.response;

import com.appcenter.study.ticketing.domain.ticket.Ticket;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TicketListResponse {
    private Long ticketStockId;
    private int quantity;
    private Ticket ticket;
}
