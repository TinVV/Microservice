package com.tin.bookservice.command.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
}
