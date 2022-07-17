package com.chaosmonkey.todoapp.service;

import com.chaosmonkey.todoapp.dto.TodoDTO;
import com.chaosmonkey.todoapp.entity.Todo;
import com.chaosmonkey.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TodoServiceTest {

    @MockBean
    private TodoRepository todoRepository;

    @Autowired
    TodoService todoService;

    @Test
    public void shouldSaveANewTodoIntoTheDatabase() {
        TodoDTO mockedTodoDTO = new TodoDTO();
        mockedTodoDTO.setTitle("Mocked Title");
        mockedTodoDTO.setBody("Mocked Body");

        Todo mappedTodo = new Todo();
        mappedTodo.setTitle("Mocked Title");
        mappedTodo.setBody("Mocked Body");
        mappedTodo.setCompleted(0);

        Todo expectedTodoResponse = new Todo();
        expectedTodoResponse.setTitle("Mocked Title");
        expectedTodoResponse.setBody("Mocked Body");
        expectedTodoResponse.setId(1);
        expectedTodoResponse.setCompleted(0);

        when(todoRepository.save(mappedTodo)).thenReturn(expectedTodoResponse);

        Todo actualTodoResponse = todoService.saveTodo(mockedTodoDTO);

        assertEquals(expectedTodoResponse, actualTodoResponse);
    }

}