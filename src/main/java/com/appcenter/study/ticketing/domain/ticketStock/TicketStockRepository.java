package com.appcenter.study.ticketing.domain.ticketStock;

import com.appcenter.study.ticketing.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketStockRepository extends JpaRepository<TicketStock, Long> {
    TicketStock findByTicket(Ticket ticket);
}
