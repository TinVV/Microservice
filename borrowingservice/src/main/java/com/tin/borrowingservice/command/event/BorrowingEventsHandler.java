package com.tin.borrowingservice.command.event;

import com.tin.borrowingservice.command.data.Borrowing;
import com.tin.borrowingservice.command.data.BorrowingRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BorrowingEventsHandler {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @EventHandler
    public void on(BorrowingCreatedEvent event){
        Borrowing borrowing = new Borrowing();
        BeanUtils.copyProperties(event, borrowing);
        borrowingRepository.save(borrowing);
    }

    @EventHandler
    public void on(BorrowingDeletedEvent event){
        Optional<Borrowing> oldEntity = borrowingRepository.findById(event.getId());
        oldEntity.ifPresent(borrowing -> borrowingRepository.delete(borrowing));
    }
}
