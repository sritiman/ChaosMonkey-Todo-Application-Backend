package com.chaosmonkey.todoapp.integration;

import com.chaosmonkey.todoapp.controller.TodoController;
import com.chaosmonkey.todoapp.dto.TodoDTO;
import com.chaosmonkey.todoapp.entity.Todo;
import com.chaosmonkey.todoapp.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TodoController.class)
public class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TodoService todoService;

    @Test
    public void createNewTodoAPITest() throws Exception {

        TodoDTO requestObject = new TodoDTO();
        requestObject.setTitle("Request todo title");
        requestObject.setBody("Request todo body");

        Todo createdTodoObject = new Todo(1, "Request todo title", "Request todo body", 0);
        String expectedResponseString = new ObjectMapper().writeValueAsString(createdTodoObject);
        String requestBodyJSON = new ObjectMapper().writeValueAsString(requestObject);
        when(todoService.saveTodo(requestObject)).thenReturn(createdTodoObject);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/todoservice/v1/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyJSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedResponseString));
    }

    @Test
    public void getAllTodosAPITest() throws Exception {

        Todo todo1 = new Todo(1, "todo Title 1", "todo body 1", 1);
        Todo todo2 = new Todo(2, "todo Title 2", "todo body 2", 0);
        List<Todo> todos = List.of(todo1, todo2);

        when(todoService.getAllTodos()).thenReturn(todos);
        String expectedJSONResponse = new ObjectMapper().writeValueAsString(todos);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todoservice/v1/todo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJSONResponse));
    }

    @Test
    public void updateTodoAPITest() throws Exception {

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("Sample Title");
        todoDTO.setBody("Sample Body");
        todoDTO.setCompleted(1);
        Todo updatedTodo = new Todo(10, "Sample Title", "Sample Body", 1);
        when(todoService.updateTodo(todoDTO, 10)).thenReturn(updatedTodo);

        String requestJSON = new ObjectMapper().writeValueAsString(todoDTO);
        String expectedJSONResponse = new ObjectMapper().writeValueAsString(updatedTodo);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todoservice/v1/todo/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJSON)

                ).andExpect(status().isOk())
                .andExpect(content().string(expectedJSONResponse));
    }
}
