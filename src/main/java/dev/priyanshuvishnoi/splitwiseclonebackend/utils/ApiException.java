package dev.priyanshuvishnoi.splitwiseclonebackend.utils;

import lombok.Data;

@Data
public class ApiException extends RuntimeException {
    private final int status;
    private final String message;
}
