package com.chaosmonkey.todoapp.service;

import com.chaosmonkey.todoapp.dto.TodoDTO;
import com.chaosmonkey.todoapp.entity.Todo;
import com.chaosmonkey.todoapp.repository.TodoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo saveTodo(TodoDTO todoDTO) {

        ModelMapper modelMapper = new ModelMapper();
        Todo todo = modelMapper.map(todoDTO, Todo.class);

        Todo savedTodo = todoRepository.save(todo);
        return savedTodo;

    }

    public List<Todo> getAllTodos() {
        return  todoRepository.findAll();
    }

    public Todo updateTodo(TodoDTO todoDTO, int id) {
        ModelMapper modelMapper = new ModelMapper();
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        todo.setId(id);

        Todo updatedTodo = todoRepository.save(todo);
        return updatedTodo;
    }
}
