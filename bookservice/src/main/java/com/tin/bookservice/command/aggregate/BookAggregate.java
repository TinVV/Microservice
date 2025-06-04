package com.tin.bookservice.command.aggregate;

import com.tin.bookservice.command.command.CreateBookCommand;
import com.tin.bookservice.command.command.DeleteBookCommand;
import com.tin.bookservice.command.command.UpdateBookCommand;
import com.tin.bookservice.command.event.BookCreateEvent;
import com.tin.bookservice.command.event.BookDeleteEvent;
import com.tin.bookservice.command.event.BookUpdateEvent;
import com.tin.commonservice.command.RollBackStatusBookCommand;
import com.tin.commonservice.command.UpdateStatusBookCommand;
import com.tin.commonservice.event.BookRollBackStatusEvent;
import com.tin.commonservice.event.BookUpdateStatusEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
@Getter
@Setter
public class BookAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private String author;
    private Boolean isReady;

    @CommandHandler
    public BookAggregate(CreateBookCommand command){
        BookCreateEvent bookCreateEvent = new BookCreateEvent();
        BeanUtils.copyProperties(command, bookCreateEvent);

        AggregateLifecycle.apply(bookCreateEvent);
    }

    @CommandHandler
    public void handle(UpdateBookCommand command){
        BookUpdateEvent bookUpdateEvent = new BookUpdateEvent();
        BeanUtils.copyProperties(command, bookUpdateEvent);

        AggregateLifecycle.apply(bookUpdateEvent);
    }

    @CommandHandler
    public void handle(DeleteBookCommand command){
        BookDeleteEvent bookDeleteEvent = new BookDeleteEvent();
        BeanUtils.copyProperties(command, bookDeleteEvent);

        AggregateLifecycle.apply(bookDeleteEvent);
    }

    @CommandHandler
    public void handler(UpdateStatusBookCommand command){
        BookUpdateStatusEvent event = new BookUpdateStatusEvent();
        BeanUtils.copyProperties(command, event);

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handler(RollBackStatusBookCommand command){
        BookRollBackStatusEvent event = new BookRollBackStatusEvent();
        BeanUtils.copyProperties(command, event);

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on (BookRollBackStatusEvent event){
        this.id = event.getBookId();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookUpdateStatusEvent event){
        this.id = event.getBookId();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookCreateEvent event){
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookUpdateEvent event){
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookDeleteEvent event){
        this.id = event.getId();
    }
}
