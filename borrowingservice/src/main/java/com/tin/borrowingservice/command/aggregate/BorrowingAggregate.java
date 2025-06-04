package com.tin.borrowingservice.command.aggregate;

import com.tin.borrowingservice.command.command.CreateBorrowingCommand;
import com.tin.borrowingservice.command.command.DeleteBorrowingCommand;
import com.tin.borrowingservice.command.event.BorrowingCreatedEvent;
import com.tin.borrowingservice.command.event.BorrowingDeletedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
@NoArgsConstructor
@Data
public class BorrowingAggregate {
    @AggregateIdentifier
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;

    @CommandHandler
    public BorrowingAggregate(CreateBorrowingCommand command) {
        BorrowingCreatedEvent borrowingCreatedEvent = new BorrowingCreatedEvent();
        BeanUtils.copyProperties(command, borrowingCreatedEvent);

        AggregateLifecycle.apply(borrowingCreatedEvent);
    }

    @EventSourcingHandler
    public void on(BorrowingCreatedEvent event){
        this.id = event.getId();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
        this.borrowingDate = event.getBorrowingDate();
    }

    @CommandHandler
    public void handle(DeleteBorrowingCommand command){
        BorrowingDeletedEvent event = new BorrowingDeletedEvent(command.getId());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowingDeletedEvent event){
        this.id = event.getId();
    }
}
