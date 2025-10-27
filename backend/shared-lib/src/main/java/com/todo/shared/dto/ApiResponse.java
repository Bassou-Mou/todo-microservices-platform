package com.todo.shared.dto;

import lombok.AllArgsConstructor; // Permet d'utiliser l'annotation @AllArgsConstructor pour générer automatiquement un constructeur avec tous les champs en arguments.
import lombok.Builder; // Permet d'utiliser l'annotation @Builder pour implémenter le patron de conception Builder, facilitant la création d'objets avec une syntaxe fluide.
import lombok.Data; // Elle génère les méthodes de base (getters et setters) pour tous les champs.
import lombok.NoArgsConstructor; // Générer un constructeur sans arguments.

import java.time.LocalDateTime; // Pour stocker la date et l'heure actuelles

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    public static <T> ApiResponse<T> success(T data){
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
    public static <T> ApiResponse<T> success(String message, T data){
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
    public static <T> ApiResponse<T> erreur(String message){
        return  ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
