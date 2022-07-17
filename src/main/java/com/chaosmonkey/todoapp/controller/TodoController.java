package com.chaosmonkey.todoapp.controller;

import com.chaosmonkey.todoapp.dto.TodoDTO;
import com.chaosmonkey.todoapp.entity.Todo;
import com.chaosmonkey.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todoservice/v1")
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<Todo> createTodo(@RequestBody TodoDTO todoDTO) {

        Todo todo = todoService.saveTodo(todoDTO);
        return new ResponseEntity(todo, HttpStatus.CREATED);

    }
}
