package com.tin.notificationservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Message {
    private String employeeId;
    private String message;

    @Override
    public String toString() {
        return "Message [employeeId=" + employeeId + ", message=" + message + "]";
    }
}
