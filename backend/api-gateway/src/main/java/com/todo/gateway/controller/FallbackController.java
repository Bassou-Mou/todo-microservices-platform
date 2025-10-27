package com.todo.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")

public class FallbackController {

    @GetMapping("/user-service")
    public ResponseEntity <Map<String, Object>> userServicesFallBack(){
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "User Service is temporarily unavailable. Please try again later.");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }
    @GetMapping("/todo-service")
    public ResponseEntity <Map<String, Object>> todoServicesFallBack(){
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "TODO Service is temporarily unavailable. Please try again later.");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }
}