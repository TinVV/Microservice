package com.tin.borrowingservice.command.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingCreateModel {
    private String bookId;
    private String employeeId;
}
