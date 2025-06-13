package com.tin.bookservice.query.controller;

import com.tin.bookservice.BookserviceApplication;
import com.tin.bookservice.query.model.BookResponseModel;
import com.tin.bookservice.query.queries.GetAllBookQuery;
import com.tin.commonservice.model.BookResponseCommonModel;
import com.tin.commonservice.queries.GetBookDetailQuery;
import com.tin.commonservice.services.KafkaService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private KafkaService kafkaService;

    private Logger logger = LoggerFactory.getLogger(BookserviceApplication.class);

    @GetMapping
    public List<BookResponseModel> getAllBooks() {
        GetAllBookQuery query = new GetAllBookQuery();
        List<BookResponseModel> lst = queryGateway
                .query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
        logger.info("LIST BOOKS: " + lst.toString());
        return lst;
    }

    @GetMapping("{bookId}")
    public BookResponseCommonModel getBookDetail(@PathVariable String bookId) {
        GetBookDetailQuery query = new GetBookDetailQuery(bookId);

        return queryGateway.query(query, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message) {
        kafkaService.sendMessage("test", message);
    }
}
