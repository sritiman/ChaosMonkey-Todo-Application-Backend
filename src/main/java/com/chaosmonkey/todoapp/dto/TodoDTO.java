package com.chaosmonkey.todoapp.dto;

import lombok.Data;

@Data
public class TodoDTO {

    String title;
    String body;
    int completed;
}
