package com.tin.bookservice.query.projection;

import com.tin.bookservice.command.data.Book;
import com.tin.bookservice.command.data.BookRepository;
import com.tin.bookservice.query.model.BookResponseModel;
import com.tin.bookservice.query.queries.GetAllBookQuery;
import com.tin.commonservice.model.BookResponseCommonModel;
import com.tin.commonservice.queries.GetBookDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {

    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery query){
        List<Book> list = bookRepository.findAll();

        List<BookResponseModel> listBookResponse = new ArrayList<>();

        list.forEach(book -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(book, model);
            listBookResponse.add(model);
        });

        return listBookResponse;
    }

    @QueryHandler
    public BookResponseCommonModel handle(GetBookDetailQuery query) throws Exception {
        BookResponseCommonModel bookResponseModel = new BookResponseCommonModel();

        Book book = bookRepository.findById(query.getId()).orElseThrow(() ->
                new Exception("Not found book with book id " + query.getId()));

        BeanUtils.copyProperties(book, bookResponseModel);

        return bookResponseModel;
    }
}
