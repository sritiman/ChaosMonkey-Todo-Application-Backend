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

import java.util.List;

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
    public void shouldReturnResponseEntityWithStatusCreatedWhenDTOIsPassedAsRequestBody() {

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

    @Test
    public void shouldReturnListOfTodosWithStatusOkWhenGetTodosControllerIsCalled() {
        Todo todo1 = new Todo(1, "todo Title 1", "todo body 1", 1);
        Todo todo2 = new Todo(2, "todo Title 2", "todo body 2", 0);

        List<Todo> todos = List.of(todo1, todo2);
        ResponseEntity expectedResponse = new ResponseEntity(todos, HttpStatus.OK);

        when(todoService.getAllTodos()).thenReturn(todos);
        ResponseEntity<List<Todo>> actualResponse = todoController.getTodos();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldReturnUpdatedTodoWithResponseStatusOkWhenUpdateTodoIsCalled() {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("Updated Title");
        todoDTO.setBody("Updated Body");
        todoDTO.setCompleted(1);

        Todo expectedTodo = new Todo(3, "Updated Title","Updated Body",1);
        ResponseEntity expectedResponse = new ResponseEntity(expectedTodo, HttpStatus.OK);
        when(todoService.updateTodo(todoDTO,3)).thenReturn(expectedTodo);

        ResponseEntity<Todo> actualResponse = todoController.updateTodo(todoDTO, 3);

        assertEquals(expectedResponse, actualResponse);
    }
}