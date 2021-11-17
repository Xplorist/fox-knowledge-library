package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultDTO<T> {
    private String code;
    private String msg;
    private T t;
}
