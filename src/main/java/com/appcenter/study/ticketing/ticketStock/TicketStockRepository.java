package com.appcenter.study.ticketing.ticketStock;

import com.appcenter.study.ticketing.entity.Ticket;
import com.appcenter.study.ticketing.entity.TicketStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketStockRepository extends JpaRepository<TicketStock, Long> {
    TicketStock findByTicket(Ticket ticket);
}
