package com.chaosmonkey.todoapp.controller;

import com.chaosmonkey.todoapp.dto.TodoDTO;
import com.chaosmonkey.todoapp.entity.Todo;
import com.chaosmonkey.todoapp.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TodoControllerTest {

    @InjectMocks
    TodoController todoController;

    @Mock
    TodoService todoService;

    @Test
    public void shouldReturnResponseEntityWhenDTOIsPassedAsRequestBody() {

        TodoDTO mockedRequestBody = new TodoDTO();
        mockedRequestBody.setTitle("Mocked title");
        mockedRequestBody.setBody("Mocked body");

        Todo savedTodo = new Todo();
        savedTodo.setId(1);
        savedTodo.setTitle("Mocked title");
        savedTodo.setBody("Mocked Body");
        savedTodo.setCompleted(0);

        when(todoService.saveTodo(mockedRequestBody)).thenReturn(savedTodo);
        ResponseEntity expectedResponse = new ResponseEntity(savedTodo, HttpStatus.CREATED);

        ResponseEntity<Todo> actualResponse = todoController.createTodo(mockedRequestBody);

        assertEquals(expectedResponse, actualResponse);
    }
}