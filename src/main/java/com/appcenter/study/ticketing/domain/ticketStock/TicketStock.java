package com.appcenter.study.ticketing.domain.ticketStock;

import com.appcenter.study.ticketing.common.BaseEntity;
import com.appcenter.study.ticketing.domain.ticket.Ticket;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "ticketstock")
public class TicketStock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketStockId;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    // 티켓 수량 감소
    public void decreaseQuantity() throws RuntimeException {
        if (this.quantity <= 0) {
            throw new RuntimeException("티켓 잔여 수량 없음");
        }
        this.quantity--;
    }
}
