package com.tin.bookservice.command.event;

import com.tin.bookservice.command.data.Book;
import com.tin.bookservice.command.data.BookRepository;
import com.tin.commonservice.event.BookRollBackStatusEvent;
import com.tin.commonservice.event.BookUpdateStatusEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookEventsHandler {
    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreateEvent event){
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdateEvent event){
        Optional<Book> oldBook = bookRepository.findById(event.getId());

        oldBook.ifPresent( book -> {
            book.setName(event.getName());
            book.setAuthor(event.getAuthor());
            book.setIsReady(event.getIsReady());

            bookRepository.save(book);
            }
        );
    }

    @EventHandler
    public void on(BookDeleteEvent event){
        Optional<Book> oldBook = bookRepository.findById(event.getId());

        oldBook.ifPresent(book -> bookRepository.delete(book));
    }

    @EventHandler
    public void on(BookUpdateStatusEvent event){
        Optional<Book> oldBook = bookRepository.findById(event.getBookId());
        oldBook.ifPresent(book -> {
            book.setIsReady(event.getIsReady());
            bookRepository.save(book);
        });
    }

    @EventHandler
    public void on(BookRollBackStatusEvent event){
        Optional<Book> oldBook = bookRepository.findById(event.getBookId());
        oldBook.ifPresent(book -> {
            book.setIsReady(event.getIsReady());
            bookRepository.save(book);
        });
    }
}
