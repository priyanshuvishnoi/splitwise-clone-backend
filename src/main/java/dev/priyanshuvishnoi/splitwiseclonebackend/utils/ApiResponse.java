package dev.priyanshuvishnoi.splitwiseclonebackend.utils;

public record ApiResponse<T>(boolean flag, T data, Msg msg) {
}