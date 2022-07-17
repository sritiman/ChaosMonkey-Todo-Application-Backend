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
}
